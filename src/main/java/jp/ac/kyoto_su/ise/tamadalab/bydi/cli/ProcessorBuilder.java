package jp.ac.kyoto_su.ise.tamadalab.bydi.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.ComparisonProcessor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.ExtractionProcessor;

public class ProcessorBuilder {
    private Map<String, Processor> processors = new HashMap<>();

    public ProcessorBuilder(){
        processors.put("extract", new ExtractionProcessor());
        processors.put("compare", new ComparisonProcessor());
        processors.put("help", new HelpProcessor());
    }

    public Processor build(String command) {
        return Optional.ofNullable(processors.get(command))
                .orElse(processors.get("help"));
    }
}
