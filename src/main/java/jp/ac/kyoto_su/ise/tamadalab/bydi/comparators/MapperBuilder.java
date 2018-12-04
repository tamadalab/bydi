package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.translators.ProGuardStore;
import jp.ac.kyoto_su.ise.tamadalab.bydi.translators.YGuardStore;

public class MapperBuilder {
    private Map<String, Supplier<StoreMapper>> mappers = new HashMap<>();

    public MapperBuilder() {
        mappers.put("bydi", () -> new DefaultMapper());
        mappers.put("proguard", () -> new ProGuardStore());
        mappers.put("yguard", () -> new YGuardStore());
    }

    public Mapper build(Optional<String> mapperName, String format) {
        return mapperName.map(name -> build(name, format))
                .orElse(new NullMapper());
    }

    private Mapper build(String name, String format) {
        Optional<StoreMapper> mapper = Optional.of(mappers.get(format).get());
        mapper.ifPresent(storeMapper -> construct(storeMapper, name));
        return mapper.map(instance -> (Mapper)instance)
                .orElse(new NullMapper());
    }

    private void construct(StoreMapper mapper, String fileName) {
        try(Stream<String> lines = Files.lines(Paths.get(fileName))) {
            mapper.start();
            lines.forEach(mapper::storeItem);
            mapper.done();
        } catch(IOException e) {
            throw new IllegalArgumentException(fileName + ": " + e.getMessage());
        }
    }
}
