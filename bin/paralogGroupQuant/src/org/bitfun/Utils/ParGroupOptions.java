package org.bitfun.Utils;


import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by vitor on 13/04/18.
 */
public class ParGroupOptions {

    private Options options = new Options();

    private Option help = new Option( "help", "print this message." );
    private Option createGroup = new Option( "group", "Create paralogy groups." );
    private Option sum = new Option( "sum", "Replace the gene ids by group ids; sum up the counts per group." );

    private Option paralogFile = Option.builder("p")
            .desc( "Three-column file: gene, it paralog and the paralog's function.")
            .hasArg()
            .argName( "PARALOGY_FILE" )
            .build();

    private Option groupFile = Option.builder("g")
            .desc( "File with the paralog group ids, function list and member genes list.")
            .hasArg()
            .argName( "GROUP_FILE" )
            .build();

    private Option countFile = Option.builder("c")
            .desc( "GTF counts.")
            .hasArg()
            .argName( "COUNT_FILE" )
            .build();

    public ParGroupOptions(){
        options.addOption(help);
        options.addOption(createGroup);
        options.addOption(sum);
        options.addOption(groupFile);
        options.addOption(countFile);
        options.addOption(paralogFile);
    }


    public Options getOptions() {
        return options;
    }
}
