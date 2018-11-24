package jp.ac.kyoto_su.ise.tamadalab.bydi.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.ComparisonProcessor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.ExtractionProcessor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.translators.TranslationProcessor;

public class ProcessorBuilder {
    private Map<String, Supplier<Processor>> processors = new HashMap<>();

    public ProcessorBuilder(){
        processors.put("extract", () -> new ExtractionProcessor());
        processors.put("compare", () -> new ComparisonProcessor());
        processors.put("translate", () -> new TranslationProcessor());
        processors.put("help", () -> new HelpProcessor());
    }

    public Processor build(String command) {
        return Optional.ofNullable(processors.get(command).get())
                .orElse(processors.get("help").get());
    }
}
