package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class MapperFactory {
    public Mapper build(Optional<String> mapperName) {
        return mapperName.map(name -> build(name))
                .orElse(new NullMapper());
    }

    private Mapper build(String name) {
        DefaultMapper mapper = new DefaultMapper();
        try(Stream<String> lines = Files.lines(Paths.get(name))) {
            mapper.construct(lines);
        } catch(IOException e) {
            throw new IllegalArgumentException(name + ": " + e.getMessage());
        }
        return mapper;
    }
}
