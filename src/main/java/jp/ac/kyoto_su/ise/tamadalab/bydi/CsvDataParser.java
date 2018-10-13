package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CsvDataParser {
    public CsvData parse(String file) throws IOException {
        return parse(Paths.get(file));
    }

    public CsvData parse(Path path) throws IOException {
        return build(Files.lines(path));
    }

    private CsvData build(Stream<String> lines) {
        return new CsvData(lines.map(this::convert));
    }

    private Data convert(String line) {
        String[] items = line.split(",");
        // System.out.printf("%d,%s%n", items.length, line);
        int count = Integer.valueOf(items[6]);
        String bytecode = count == 0? "": items[8];
        return new Data(items[3], items[4], items[5], bytecode(bytecode));
    }

    int[] bytecode(String items) {
        return IntStream.iterate(0, index -> index + 2)
                .takeWhile(index -> index < items.length())
                .mapToObj(index -> items.substring(index, index + 2))
                .mapToInt(value -> Integer.parseInt(value, 16))
                .toArray();
    }
}
