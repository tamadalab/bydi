package jp.ac.kyoto_su.ise.tamadalab.bydi.cli;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public interface Processor {
    default Arguments parse(List<String> args) throws CmdLineException {
        Arguments arguments = buildArguments();
        CmdLineParser parser = new CmdLineParser(arguments);
        parser.parseArgument(args);
        return arguments;
    }

    default Arguments parse(String[] args) throws CmdLineException {
        return parse(Arrays.stream(args)
                .collect(Collectors.toList()));
    }
    
    Arguments buildArguments();

    void perform(Arguments args);
}
