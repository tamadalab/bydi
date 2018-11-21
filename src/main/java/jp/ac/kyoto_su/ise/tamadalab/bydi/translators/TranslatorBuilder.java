package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class TranslatorBuilder {
    private Map<String, Supplier<Translator>> builders = new HashMap<>();
    
    public TranslatorBuilder() {
        builders.put("proguard", () -> new ProGuardTranslator());
    }

    public Optional<Translator> get(String key) {
        return Optional.ofNullable(builders.get(key))
                .map(supplier -> supplier.get());
    }
}
