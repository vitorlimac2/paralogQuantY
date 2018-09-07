package org.bitfun;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;

public class Main {


    private static Options options = new Options();

    private static CommandLine cmd;

    private static HashMap<String, List<String>> ortologGroups = new HashMap<>();
    private static HashMap<String, List<String>> ortologFunctions = new HashMap<>();

    private static Map<String, String> groupHash;

    private static Map<String, FeatureCount> countHash;

    public static void main(String[] args) {
        // write your code here

        if (!validadeOptions(args)) {
            help();
            System.exit(-1);
        }

        if (isGroup()) {
            group();
        } else if (isSum()) {
            sumCount();
        }

        // read line
        // check if $1 or $2 is element of a list l1;
        /// if yes; add $1 and $2 to
        /// if no; create new list and add $1,$2

        /*
        String filename = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tuple = line.split("\t");
                addToGroup(tuple);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        /* It is old, but it works.

        for(Map.Entry<String,List<String>> group: ortologGroups.entrySet()){
            System.out.println("@GROUP_SIZE\t" + "og"+group.getValue().size());
            String sedCommand="";
            System.out.println("@FUNCTION\t" + "og"+group.getKey()+"\t"+ ortologFunctions.get(group.getKey()));
            System.out.println("@GROUP\t" + "og"+group.getKey()+"\t"+ group.getValue());
            for(String gene: group.getValue()){
                String mygene = gene.replace(".","\\.");
                sedCommand += "s/\\<"+mygene+"\\>/og"+group.getKey()+"/g";
                System.out.println("@MY_SED_COMMAND " + sedCommand);
                sedCommand="";
            }
        }
        */
    }


    private static void createGroup(String[] tuple) {

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add(tuple[0]);
        l1.add(tuple[1]);

        ortologGroups.put(tuple[0], l1);

        l2.add(tuple[3]);
        ortologFunctions.put(tuple[0], l2);
    }

    private static void addFunction(String key, String func) {

        if (!ortologFunctions.get(key).contains(func)) {
            ortologFunctions.get(key).add(func);
        }

    }

    private static void addToGroup(String[] tuple) {
        if (ortologGroups.size() == 0) {
            createGroup(tuple);
            return;
        }

        for (Map.Entry<String, List<String>> group : ortologGroups.entrySet()) {
            if (group.getValue().contains(tuple[0])) { // if exists a group with id tuple[0]
                if (!group.getValue().contains(tuple[1])) { // this group does not contains tuple[1]
                    group.getValue().add(tuple[1]);
                    addFunction(group.getKey(), tuple[3]);
                    return;
                }
                return;
            } else if (group.getValue().contains(tuple[1])) {
                group.getValue().add(tuple[0]);
                addFunction(group.getKey(), tuple[3] + " |");
                return;
            }
        }

        createGroup(tuple);
    }

    private static void initializeOptions() {
        //Create the options
        Option help = new Option("help", "print this message.");
        options.addOption(help);

        Option createGroup = Option.builder("group").desc("Create paralog groups.").build();
        options.addOption(createGroup);

        Option sum = new Option("sum", "Replace the gene ids by group ids; sum up the counts per group.");
        options.addOption(sum);

        Option groupFile = Option.builder("g")
                .desc("File with the paralog group ids, function list and member genes list.")
                .hasArg()
                .argName("GROUP_FILE")
                .build();
        options.addOption(groupFile);

        Option countFile = Option.builder("c")
                .desc("GTF counts.")
                .hasArg()
                .argName("COUNT_FILE")
                .build();
        options.addOption(countFile);

        Option paralogFile = Option.builder("p")
                .desc("Three-column file: gene, it paralog and the paralog's function.")
                .argName("PARALOGY_FILE")
                .hasArg()
                .build();
        options.addOption(paralogFile);

    }

    private static boolean validadeOptions(String[] args) {

        initializeOptions();

        // Parse the args
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

        if (cmd.hasOption("help")) {
            return false;
        }

        if (cmd.hasOption("sum") && cmd.hasOption("count")) {
            System.err.println("ERROR: You should select only one option: -sum OR -count.");
            return false;
        }

        if (cmd.hasOption("group") &&
                (!cmd.hasOption('p') ||
                        cmd.getOptionValue('p') == null)) {
            System.err.println("ERROR: Paralog information file option (-p) is missing and it is necessary" +
                    " for you selected option -group.");
            return false;
        }

        if (cmd.hasOption("sum") &&
                (!cmd.hasOption('g') ||
                        !cmd.hasOption('c') ||
                        cmd.getOptionValue('g') == null ||
                        cmd.getOptionValue('c') == null)) {
            System.err.println("POSSIBLE ERRORS:");
            System.err.println("\t\t- Paralog group file (-g) is missing.\n" +
                    "\t\t- GTF count file (-c) is missing.\t" +
                    "These files are necessary for the selected option -sum.");
            return false;
        }
        return true;
    }

    public static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("paralogGroupQuant", options);
        System.out.println("Examples:");
        System.out.println("* java -jar paralogGroupQuant.jar -group -p <PARALOGY_FILE>\n\tCreate paralogy groups.");
        System.out.println("* java -jar paralogGroupQuant.jar -sum -g <GROUP_FILE> -c <COUNT_FILE>\n\tReplace the gene ids by group ids; sum up the counts per group.");
    }

    private static boolean isGroup() {
        return cmd.hasOption("group");
    }

    private static boolean isSum() {
        return cmd.hasOption("sum");
    }

    private static String getParalogInfoFileName() {
        return cmd.getOptionValue('p');
    }

    private static String getGTFcountFileName() {
        return cmd.getOptionValue("c");
    }

    private static String getGroupsFileName() {
        return cmd.getOptionValue("g");
    }

    private static void group() {

        String filename = getParalogInfoFileName();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tuple = line.split("\t");
                addToGroup(tuple);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        printGroup();

    }

    private static void sumCount() {

        try {
            parseGroupFile();
            parseCount();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printGroupCount();

    }

    private static void printGroupCount() {


        for(Map.Entry<String, FeatureCount> entry: countHash.entrySet()){

            System.out.println(entry.getKey() + "\t" + entry.getValue().getGeneListToString() + "\t" + entry.getValue().getCountListToString());

        }


    }

    private static void printGroup() {
        int idGroup = 1;
        for (Map.Entry<String, List<String>> g : ortologGroups.entrySet()) {
            System.out.print("gPar." + idGroup);
            printListString(g.getValue());
            printListString(ortologFunctions.get(g.getKey()));
            System.out.println();
            idGroup++;
        }


    }

    private static void printListString(List<String> l) {

        if (l.size() == 1) {
            System.out.print("\t" + l.get(0));
            return;
        }

        StringBuilder aux = new StringBuilder();

        for (String s : l) {
            if (aux.toString().equals("")) {
                aux = new StringBuilder(s);
            } else {
                aux.append("|").append(s);
            }
        }
        System.out.print("\t" + aux);
    }

    private static void parseGroupFile() throws FileNotFoundException {
        groupHash = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(getGroupsFileName()))) {
            scanner.useDelimiter(System.getProperty("line.separator"));
            while (scanner.hasNext()) {
                String s = scanner.next();
                String [] line = s.split("\\t");
                String group = line[0];
                String geneIdsSepByPipe = line[1];
                String[] geneIds = geneIdsSepByPipe.split("\\|");
                for (String gene : geneIds)
                groupHash.put(gene, group);
            }
        }
    }

    private static void parseCount() throws FileNotFoundException {

        countHash = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(getGTFcountFileName()))) {
            scanner.useDelimiter(System.getProperty("line.separator"));
            while (scanner.hasNext()) {
                String[] line = scanner.next().split("\\t");
                String gene = line[0];
                float[] countList = convertStringArrayToFloat(Arrays.copyOfRange(line, 1, line.length));

                String group;
                if(groupHash.containsKey(gene)) { // gene is contained in some group
                    group = groupHash.get(gene);

                    if(countHash.containsKey(group)){ // this group is already added in the hash
                        updateFeature(group, gene,countList);

                    }else{ // this group is not added in the hash.
                        createNewFeature(group, gene, countList);
                    }

                }else{ // gene has no group
                    createNewFeature(gene,countList);
                }
            }
        }

    }

    private static void createNewFeature(String id, float[] counts){
        createNewFeature(id, id, counts);
    }

    private static void createNewFeature(String id, String gene, float[] counts){
        FeatureCount f = new FeatureCount();
        f.setGroupId(id);
        List<String> l = new ArrayList<>();
        l.add(gene);
        f.setGeneList(l);
        f.setCounts(counts);
        countHash.put(id, f);
    }

    private static void updateFeature(String group, String gene, float[] counts){
        for(int i = 0; i < counts.length; i++){
            countHash.get(group).updateCounts(i,counts[i]);
        }
        countHash.get(group).updateGeneList(gene);
    }

    private static float[] convertStringArrayToFloat(String[] slist){
        float[] flist = new float[slist.length];
        for (int i = 0; i < slist.length;i++){
            flist[i] = Float.valueOf(slist[i]);
        }
        return flist;
    }
}
