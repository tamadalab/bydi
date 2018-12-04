package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.StoreMapper;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;
import jp.ac.kyoto_su.ise.tamadalab.bydi.utils.TypeUtils;

public class ZKMStore implements StoreMapper {
    private Store store;
    private Map<NamePair, List<MethodInfoPair>> map;
    private Part part = Part.Unknown;
    
    private static enum Part {
        Unknown, Class, Source, Fields, Methods, Package, Comments;
    }

    @Override
    public void start() {
        store = new TempStore();
    }

    @Override
    public void storeItem(String line, boolean flag) {
        if(line.startsWith("Class:")) {
            part = Part.Class;
            store.storeItem(stripModifiers(line.substring("Class: ".length())), false);
        }
        else if(line.startsWith("Package"))      part = Part.Package;
        else if(line.startsWith("//"))           part = Part.Comments;
        else if(line.startsWith("\tSource:"))    part = Part.Source;
        else if(line.startsWith("\tFieldsOf:"))  part = Part.Fields;
        else if(line.startsWith("\tMethodsOf:")) part = Part.Methods;
        else if(line.equals(""))                 part = Part.Unknown;
        else if(line.startsWith("\t\t") && part == Part.Methods) {
            store.storeItem(stripModifiers(line.trim()), true);
        }
    }

    private static final List<String> MODIFIERS =
            Arrays.asList("public", "private", "protected", "static", "final", "transient", "abstract");

    String stripModifiers(String declaration) {
        return Arrays.stream(declaration.split(" "))
                .filter(item -> !MODIFIERS.contains(item))
                .collect(Collectors.joining(" "));
    }

    @Override
    public void done() {
        store.done();
        Map<String, String> map = constructClassNameMap(((TempStore)store).keys());
        this.map = ((TempStore)store).plainStream()
            .map(item -> convertToNewPair(item, map))
            .collect(Collectors.toMap(item -> item.map((a, b) -> a), item -> item.map((a, b) -> b)));
        store = new NullStore();
    }

    private Pair<NamePair, List<MethodInfoPair>> convertToNewPair(Pair<String, List<String>> pair, Map<String, String> classNameMap){
        NamePair namePair = constructNamePair(pair);
        return new Pair<>(namePair,
                          convertToMemberPairList(namePair, pair.map((left, right) -> right), classNameMap));
    }

    private List<MethodInfoPair> convertToMemberPairList(NamePair pair, List<String> list, Map<String, String> classNameMap) {
        return list.stream()
                .map(line -> convertToMemberPair(pair, line, classNameMap))
                .collect(Collectors.toList());
    }

    private MethodInfoPair convertToMemberPair(NamePair pair, String line, Map<String, String> classNameMap) {
        if(line.contains("\t=>\t")) {
            return convertToMethodInfoPair(pair, line.split("\t=>\t"), classNameMap);
        }
        return buildNoChangedPair(pair, line.substring(0, line.indexOf("SignatureNotChanged")).trim(), classNameMap);
    }

    private MethodInfoPair buildNoChangedPair(NamePair pair, String line, Map<String, String> classNameMap) {
        String returnType = line.substring(0, line.indexOf(' '));
        String argumentTypes = between(line, '(', ')');
        String methodName = between(line, ' ', '(');
        MethodInfo before = new MethodInfo(pair.map((b, a) -> b), methodName, constructSignature(returnType, argumentTypes));
        MethodInfo info = new MethodInfo(pair.map((b, a) -> a), methodName, constructSignature(returnType, argumentTypes, classNameMap));
        return new MethodInfoPair(before, info);
    }

    private MethodInfoPair convertToMethodInfoPair(NamePair pair, String[] items, Map<String, String> classNameMap) {
        System.out.printf("%s <%s>%n", pair.map((a, b) -> String.format("<%s, %s>", a, b)), Arrays.toString(items));
        String returnType = items[0].substring(0, items[0].indexOf(" "));
        String argumentTypes = between(items[0], '(', ')');
        String oldMethodName = between(items[0], ' ', '(');
        String newMethodName = items[1].substring(0, items[1].indexOf('('));
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
        int startIndex = source.indexOf(firstChar);
        int endIndex = source.indexOf(endChar);
        if(startIndex >= 0 && endIndex > startIndex)
            return source.substring(startIndex + 1, endIndex);
        return source;
    }

    private NamePair constructNamePair(Pair<String, List<String>> pair) {
        String[] items = pair.map((left, right) -> left.split("\t=>\t"));
        if(items.length == 1)
            return new NamePair(items[0], items[0]);
        return new NamePair(items[0], items[1]);
    }

    private Map<String, String> constructClassNameMap(Stream<String> stream){
        return stream.map(line -> line.split("\t=>\t"))
                .collect(Collectors.toMap(items -> items[0], items -> items[1]));
    }

    @Override
    public Stream<MethodInfoPair> stream() {
        return map.values().stream()
                .flatMap(list -> list.stream());
    }

    @Override
    public Optional<MethodInfo> map(MethodInfo info) {
        return map.entrySet().stream()
                .filter(entry -> match(entry.getKey(), info.className()))
                .findFirst()
                .flatMap(entry -> filter(info, entry.getValue()));
    }

    private Optional<MethodInfo> filter(MethodInfo info, List<MethodInfoPair> list) {
        return list.stream()
                .filter(pair -> pair.matchBefore(info))
                .map(pair -> pair.map((before, after) -> after))
                .findFirst();
    }

    private boolean match(NamePair pair, String className) {
        return pair.test((before, after) -> Objects.equals(before, className));
    }
}
