package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class TempStore implements Store {
    private Map<String, List<String>> map = new HashMap<>();
    private List<String> lastClass = new ArrayList<>();

    @Override
    public void storeItem(String line, boolean memberFlag) {
        if(memberFlag) {
            lastClass.add(line.trim());
        }
        map.put(line, lastClass = new ArrayList<>());
    }

    public List<String> get(String line) {
        return map.get(line);
    }

    public Stream<String> keys() {
        return map.keySet().stream();
    }

    public Stream<Pair<String, List<String>>> stream() {
        return map.entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public void done() {
        // do nothing.
    }
}
