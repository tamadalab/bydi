package jp.ac.kyoto_su.ise.tamadalab.bydi.cli;

import java.util.HashMap;
import java.util.Map;

public class HelpProcessor implements Processor {
    private Map<String, Runnable> helpPrinters = new HashMap<>();
    private Runnable defaultPrinter = () -> printHelp();
    
    public HelpProcessor() {
        helpPrinters.put("compare", () -> printCompareHelp());
        helpPrinters.put("extract", () -> printExtractHelp());
        helpPrinters.put("help", () -> printHelpHelp());
    }

    @Override
    public Arguments buildArguments() { 
        return new Arguments();
    }

    @Override
    public void perform(Arguments args) {
        args.stream().findFirst()
        .ifPresentOrElse(command -> printHelp(command), defaultPrinter);
    }
    
    public static void printHelp() {
        System.out.println("Usage: java bydi.jar <COMMAND>");
        System.out.println("COMMAND");
        System.out.println("    extract: extract opcode list from given jar file.");
        System.out.println("    compare: calculate distance between given two opcode lists.");
        System.out.println("    help:    print this message.");
    }

    private void printHelp(String command) {
        helpPrinters.getOrDefault(command, defaultPrinter).run();
    }

    private void printExtractHelp() {
        System.out.println("Usage: java bydi.jar extract <JARFILE...>");
        System.out.println("  This command extracts opcode list of each method from given jar files.");
        System.out.println("  The result format is the following csv file in each line.");
        System.out.println("    class_name,method_name,method_signature,length_of_opcode,opcodes");
    }

    private void printHelpHelp() {
        System.out.println("Usage: java bydi.jar help [COMMAND]");
        System.out.println("  This command shows the help message of this tool and each command.");
        System.out.println("");
        System.out.println("ARGUMENTS");
        System.out.println("    COMMAND: Specify the command to show the help.");
        System.out.println("             If not specified this argument, the default help message is printed.");
        System.out.println("             The available values are: help, extract, and command.");
    }

    private void printCompareHelp() {
        System.out.println("Usage: java bydi.jar compare <CSV1|JAR1> <CSV2|JAR2>");
        System.out.println("  This command compares the opcode lists between given two jar or csv files.");
        System.out.println();
        System.out.println("OPTION");
        System.out.println("    -a, --algorithm <ALGORITHM>: Specify the algorithm for calculating the distance.");
        System.out.println("                                 Available algorithms: edit, manhattan, euclidean.");
        System.out.println("                                 Default is edit.");
        System.out.println("    -m, --mapping <MAPPING>:     Specify the mapping file, describing the names");
        System.out.println("                                 before and after obfuscation.");
        System.out.println("ARGUMENTS");
        System.out.println("    CSV|JAR: This argument shows csv or jar files.");
        System.out.println("             The csv file must be formatted by the extract command.");
    }
}
