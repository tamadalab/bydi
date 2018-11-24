package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.Objects;
import java.util.Optional;

import org.kohsuke.args4j.Option;

import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;

public class TranslatorArguments extends Arguments {
    @Option(name="-f", aliases="--format", required=true, usage="Specify the tool name of mapping.")
    private String type;

    public String format() {
        return type;
    }

    public Optional<String> get(String keyword) {
        if(Objects.equals(keyword, "format"))
            return Optional.of(type);
        return super.get(keyword);
    }
}
