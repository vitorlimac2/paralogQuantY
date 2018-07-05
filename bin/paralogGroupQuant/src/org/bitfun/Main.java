package org.bitfun;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {


    private static Options options = new Options();

    private static CommandLine cmd;

    private static HashMap<String, List<String>> ortologGroups = new HashMap<>();
    private static HashMap<String, List<String>> ortologFunctions = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException, ParseException {
	// write your code here

        try {
            if (!validadeOptions(args)) {
                help();
                System.exit(-1);
            }
        }catch(ParseException exp){
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

        if(isGroup()){
            group();
        }else if(isSum()){
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

        /* It is old, but gold.

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


    private static boolean createGroup(String[] tuple){

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add(tuple[0]);
        l1.add(tuple[1]);

        ortologGroups.put(tuple[0],l1);

        l2.add(tuple[3]);
        ortologFunctions.put(tuple[0],l2);
        return true;
    }

    private static boolean addFunction(String key, String func){

        if(!ortologFunctions.get(key).contains(func)){
            ortologFunctions.get(key).add(func);
        }
        return true;

    }

    private static boolean addToGroup(String[] tuple){
        if(ortologGroups.size()==0){
            createGroup(tuple);
            return true;
        }

        for(Map.Entry<String,List<String>> group: ortologGroups.entrySet()){
            if(group.getValue().contains(tuple[0])){ // if exists a group with id tuple[0]
                if(!group.getValue().contains(tuple[1])){ // this group does not contains tuple[1]
                    group.getValue().add(tuple[1]);
                    addFunction(group.getKey(),tuple[3]);
                    return true;
                }
                return false;
            }else if(group.getValue().contains(tuple[1])){
                group.getValue().add(tuple[0]);
                addFunction(group.getKey(),tuple[3]+" |");
                return true;
            }
        }

        return createGroup(tuple);
    }

    private static boolean initializeOptions(){
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

        return true;
    }

    private static boolean validadeOptions(String[] args) throws ParseException {

        initializeOptions();

        // Parse the args
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        }
        catch(ParseException exp){
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

        if(cmd.hasOption("help")){
            return false;
        }

        if(cmd.hasOption("sum") && cmd.hasOption("count")){
            System.err.println("ERROR: You should select only one option: -sum OR -count.");
            return false;
        }

        if(cmd.hasOption("group") &&
                (!cmd.hasOption('p') ||
                        cmd.getOptionValue('p') == null)){
            System.err.println("ERROR: Paralog information file option (-p) is missing and it is necessary" +
                    " for you selected option -group.");
            return false;
        }

        if(cmd.hasOption("sum") &&
                (!cmd.hasOption('g') ||
                        !cmd.hasOption('c') ||
                        cmd.getOptionValue('g') == null ||
                        cmd.getOptionValue('c') == null)){
            System.err.println("POSSIBLE ERRORS:");
            System.err.println("\t\t- Paralog group file (-g) is missing.\n" +
                    "\t\t- GTF count file (-c) is missing.\t" +
                    "These files are necessary for the selected option -sum.");
            return false;
        }
        return true;
    }

    public static void help(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "paralogGroupQuant", options);
        System.out.println("Examples:");
        System.out.println("* java -jar paralogGroupQuant.jar -group -p <PARALOGY_FILE>\n\tCreate paralogy groups.");
        System.out.println("* java -jar paralogGroupQuant.jar -sum -g <GROUP_FILE> -c <COUNT_FILE>\n\tReplace the gene ids by group ids; sum up the counts per group.");
    }


    private static boolean isGroup(){
        return cmd.hasOption("group");
    }

    private static boolean isSum(){
        return options.hasLongOption("sum");
    }

    private static String getParalogInfoFileName(){
        return cmd.getOptionValue('p');
    }

    private static String getGTFcountFileName(){
        return cmd.getOptionValue("c");
    }

    private static String getGroupsFileName(){
        return cmd.getOptionValue("g");
    }

    private static void group(){

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

    private static void sumCount(){

    }

    private static void printGroup(){
        int idGroup=1;
        for(Map.Entry<String, List<String>> g : ortologGroups.entrySet()){
            System.out.print("gPar."+idGroup);
            printListString(g.getValue());
            printListString(ortologFunctions.get(g.getKey()));
            System.out.println();
            idGroup++;
        }


    }

    private static void printListString(List<String> l){

        if(l.size()==1){
            System.out.print("\t" + l.get(0));
            return;
        }

        String aux="";

        for(String s: l){
            if(aux.equals("")){
                aux=s;
            }
            else{
                aux=aux+"|"+s;
            }
        }
        System.out.print("\t"+aux);
    }
}
