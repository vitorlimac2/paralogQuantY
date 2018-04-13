package org.bitfun.Utils;


import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by vitor on 13/04/18.
 */
public class ParGroupOptions {

    private Options options = new Options();

    private Option help = new Option( "help", "print this message" );
    private Option createGroup = new Option( "create", "print this message" );
    private Option sum = new Option( "sum", "print this message" );

    private Option paralogFile = Option.builder("p")
            .desc( "Three-column file: gene, it paralog and the paralog's function")
            .hasArg()
            .argName( "PARALOGY_FILE" )
            .build();

    private Option groupFile = Option.builder("g")
            .longOpt( "group" )
            .desc( "File with the paralog group ids, function list and member genes list")
            .hasArg()
            .argName( "GROUP_FILE" )
            .build();

    private Option countFile = Option.builder("c")
            .longOpt( "count" )
            .desc( "GTF weighted counts")
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


}
