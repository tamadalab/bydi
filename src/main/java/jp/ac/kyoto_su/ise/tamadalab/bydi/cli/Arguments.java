package jp.ac.kyoto_su.ise.tamadalab.bydi.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.kohsuke.args4j.Argument;

public class Arguments {
    @Argument
    private List<String> args = new ArrayList<>();

    public Arguments() {
    }

    public int size() {
        return args.size();
    }

    public Optional<String> get(String keyword) {
        return Optional.empty();
    }

    public String get(String keyword, String defaultValue) {
        return get(keyword).orElse(defaultValue);
    }

    public Stream<String> stream(){
        return args.stream();
    }

    public Stream<String> streamOrElse(Runnable action) {
        if(args.size() == 0)
            action.run();
        return stream();
    }
}
