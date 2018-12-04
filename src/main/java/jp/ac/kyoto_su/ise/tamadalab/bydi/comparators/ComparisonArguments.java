package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.Objects;
import java.util.Optional;

import org.kohsuke.args4j.Option;

import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;

public class ComparisonArguments extends Arguments {
    @Option(name="-a", aliases="--algorithm", metaVar="ALGORITHM", usage="Specify the algorithm for calculating the distance between two opcode list.")
    private String algorithm = "edit";

    @Option(name="-f", aliases="--format", metaVar="FORMAT", usage="Specify the format of the mapping file.  Default is bydi. Available values are: null, bydi, proguard.")
    private String format = "bydi";

    @Option(name="-m", aliases="--mapper", metaVar="FILE", usage="Specify the name mapping file.")
    private String mapping;

    public Optional<String> get(String keyword) {
        if(Objects.equals(keyword, "algorithm")) {
            return Optional.of(algorithm);
        }
        else if(Objects.equals(keyword, "mapper")) {
            return Optional.ofNullable(mapping);
        }
        else if(Objects.equals(keyword, "format")) {
            return Optional.of(format);
        }
        return Optional.empty();
    }

    @Override
    public String get(String keyword, String defaultValue) {
        return get(keyword).orElse(defaultValue);
    }
}

