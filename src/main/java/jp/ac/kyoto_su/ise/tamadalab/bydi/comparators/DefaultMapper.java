package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.translators.MethodInfoPair;

public class DefaultMapper implements StoreMapper {
    private Map<String, Map<String, Map<String, MethodInfoPair>>> map = new HashMap<>();

    public Optional<MethodInfo> map(MethodInfo info) {
        return Optional.ofNullable(map.getOrDefault(info.className(), new HashMap<>())
                .getOrDefault(info.methodName(), new HashMap<>())
                .get(info.signature())
                .map((before, after) -> after));
    }

    private void store(String[] lines) {
        MethodInfo from = new MethodInfo(lines[0], lines[1], lines[2]);
        MethodInfo to = new MethodInfo(lines[3], lines[4], lines[5]);
        store(from, to);
    }

    private void store(MethodInfo from, MethodInfo to) {
        Map<String, Map<String, MethodInfoPair>> map2 = map.getOrDefault(from.className(), new HashMap<>());
        Map<String, MethodInfoPair> map3 = map2.getOrDefault(from.methodName(), new HashMap<>());
        map3.put(from.signature(), new MethodInfoPair(from, to));
        map2.put(from.methodName(), map3);
        map.put(from.className(),  map2);
    }

    @Override
    public void storeItem(String line, boolean memberFlag) {
        store(line.split(","));
    }

    @Override
    public void done() {
        // do nothing.
    }

    @Override
    public Stream<MethodInfoPair> stream() {
        return map.values().stream()
                .flatMap(map -> map.values().stream())
                .flatMap(map -> map.values().stream());
    }
}
