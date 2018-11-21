package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class ProGuardStore implements Store {
    private TempStore store;

    @Override
    public void start() {
        store = new TempStore();
    }

    @Override
    public void storeItem(String line, boolean flag) {
        boolean memberFlag = line.startsWith("    ") || line.startsWith("\t");
        line = removeLastColonIfExists(line);
        store.storeItem(line.trim(), memberFlag);
    }

    @Override
    public void done() {
        Map<String, String> map = constructClassNameMap(store.keys());
        store.stream()
        .map(item -> convertToNewPair(item, map));
    }
    private Pair<NamePair, List<Pair<MethodInfo, MethodInfo>>> convertToNewPair(Pair<String, List<String>> pair, Map<String, String> classNameMap){
        NamePair namePair = constructNamePair(pair);
        return new Pair<>(namePair,
                convertToMethodInfoPairList(pair.map((left, right) -> right), classNameMap));
    }

    private List<Pair<MethodInfo, MethodInfo>> convertToMethodInfoPairList(List<String> list, Map<String, String> classNameMap){
        return list.stream()
                .map(line -> convertToMethodInfoPair(line, classNameMap))
                .collect(Collectors.toList());
    }

    private Pair<MethodInfo, MethodInfo> convertToMethodInfoPair(String line, Map<String, String> classNameMap) {
        return null;
    }

    private NamePair constructNamePair(Pair<String, List<String>> pair) {
        String[] items = pair.map((left, right) -> left.split(" -> "));
        return new NamePair(items[0], items[1]);
    }

    private Map<String, String> constructClassNameMap(Stream<String> stream){
        return stream.map(line -> line.split(" -> "))
                .collect(Collectors.toMap(items -> items[0], items -> items[1]));
    }
    
    private String removeLastColonIfExists(String line) {
        if(line.endsWith(":"))
            return line.substring(0, line.length() - 1);
        return line;
    }
}
