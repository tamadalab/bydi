package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.args4j.CmdLineException;

import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Processor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.ProcessorBuilder;

public class Main {
    public void run(String[] args) {
        List<String> newArgs = shift(args);
        perform(new ProcessorBuilder().build(args[0]), newArgs);
    }

    private void perform(Processor processor, List<String> args) {
        try{
            Arguments arguments = processor.parse(args);
            processor.perform(arguments);
        } catch(CmdLineException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<String> shift(String[] args) {
        return Arrays.stream(args)
                .skip(1)
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run(args);
    }
}
