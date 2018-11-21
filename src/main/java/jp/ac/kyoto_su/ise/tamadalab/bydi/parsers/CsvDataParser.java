package jp.ac.kyoto_su.ise.tamadalab.bydi.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.DataPoolBuilder;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.DataStore;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.DataPool;

public class CsvDataParser implements DataPoolBuilder {
    @Override
    public Optional<DataPool> extract(String file) {
        try{
            return Optional.of(parse(Paths.get(file)));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public DataPool parse(Path path) throws IOException {
        return build(Files.lines(path));
    }

    private DataPool build(Stream<String> lines) {
        return new DataStore(lines.map(this::convert));
    }

    private Method convert(String line) {
        String[] items = line.split(",");
        // System.out.printf("%d,%s%n", items.length, line);
        int count = Integer.valueOf(items[3]);
        String bytecode = count == 0? "": items[4];
        return new Method(new MethodInfo(items[0], items[1], items[2]),
                Optional.ofNullable(bytecode(bytecode)));
    }

    int[] bytecode(String items) {
        return IntStream.iterate(0, index -> index + 2)
                .takeWhile(index -> index < items.length())
                .mapToObj(index -> items.substring(index, index + 2))
                .mapToInt(value -> Integer.parseInt(value, 16))
                .toArray();
    }
}
