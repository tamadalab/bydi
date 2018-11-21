package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class DefaultMapper implements Mapper {
    private Map<String, Map<String, Map<String, MethodInfo>>> map = new HashMap<>();

    public void construct(Path path) throws IOException {
        this.construct(Files.lines(path));
    }

    public void construct(Stream<String> stream) {
        stream.map(line -> line.split(","))
        .forEach(this::store);
    }

    public Optional<MethodInfo> map(MethodInfo info) {
        return Optional.ofNullable(map.getOrDefault(info.className(), new HashMap<>())
                .getOrDefault(info.methodName(), new HashMap<>())
                .get(info.signature()));
    }

    private void store(String[] lines) {
        MethodInfo from = new MethodInfo(lines[0], lines[1], lines[2]);
        MethodInfo to = new MethodInfo(lines[3], lines[4], lines[5]);
        store(from, to);
    }

    public void store(MethodInfo from, MethodInfo to) {
        Map<String, Map<String, MethodInfo>> map2 = map.getOrDefault(from.className(), new HashMap<>());
        Map<String, MethodInfo> map3 = map2.getOrDefault(from.methodName(), new HashMap<>());
        map3.put(from.signature(), to);
        map2.put(from.methodName(), map3);
        map.put(from.className(),  map2);
    }
}
