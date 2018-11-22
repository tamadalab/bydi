package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;
import jp.ac.kyoto_su.ise.tamadalab.bydi.utils.TypeUtils;

public class ProGuardStore implements Store {
    private TempStore store;
    private Map<NamePair, List<MethodInfoPair>> map;

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
        this.map = store.stream()
            .map(item -> convertToNewPair(item, map))
            .collect(Collectors.toMap(item -> item.map((a, b) -> a), item -> item.map((a, b) -> b)));
        store = new NullStore();
    }

    private Pair<NamePair, List<MethodInfoPair>> convertToNewPair(Pair<String, List<String>> pair, Map<String, String> classNameMap){
        NamePair namePair = constructNamePair(pair);
        return new Pair<>(namePair,
                          convertToMethodInfoPairList(namePair, pair.map((left, right) -> right), classNameMap));
    }

    private List<MethodInfoPair> convertToMethodInfoPairList(NamePair pair, List<String> list, Map<String, String> classNameMap){
        return list.stream()
            .map(line -> convertToMethodInfoPair(pair, line, classNameMap))
            .collect(Collectors.toList());
    }

    private MethodInfoPair convertToMethodInfoPair(NamePair pair, String line, Map<String, String> classNameMap) {
        String[] items = line.split(" -> ");
        String returnType = items[0].substring(0, items[0].indexOf(" "));
        String argumentTypes = between(items[0], '(', ')');
        String oldMethodName = between(items[0], ' ', '(');
        String newMethodName = items[1];
        MethodInfo beforeInfo = new MethodInfo(pair.map((b, a) -> b), oldMethodName, constructSignature(returnType, argumentTypes));
        MethodInfo afterInfo = new MethodInfo(pair.map((b, a) -> a), newMethodName, constructSignature(returnType, argumentTypes, classNameMap));
        return new MethodInfoPair(beforeInfo, afterInfo);
    }

    private String constructSignature(String returnType, String argumentTypes, Map<String, String> map){
        return constructSignature(map.getOrDefault(returnType, returnType),
                                  Arrays.stream(argumentTypes.split(", ?"))
                                  .map(arg -> map.getOrDefault(arg, arg))
                                  .toArray(String[]::new));
    }

    private String constructSignature(String returnType, String argumentTypes){
            return constructSignature(returnType, argumentTypes.split(", ?"));
    }

    private String constructSignature(String returnType, String[] argumentTypes){
        return TypeUtils.toDescriptor(returnType, argumentTypes);
    }

    private String between(String source, char firstChar, char endChar){
        return source.substring(source.indexOf(firstChar) + 1, source.indexOf(endChar));
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
