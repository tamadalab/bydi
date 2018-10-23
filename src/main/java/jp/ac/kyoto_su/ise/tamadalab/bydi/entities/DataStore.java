package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;

public class DataStore implements DataPool {
    private Map<String, List<Method>> pool = new HashMap<>();

    public DataStore() {
    }

    public DataStore(Stream<Method> stream) {
        this.pool = stream.collect(Collectors.groupingBy(m -> m.className()));
    }

    @Override
    public void pour(Method m) { 
        List<Method> list = pool.getOrDefault(m.className(), new ArrayList<>());
        if(throwIfFound(list, m)) {
            list.add(m);
        }
        pool.put(m.className(), list);
    }

    @Override
    public Stream<Method> stream() {
        return pool.values().stream()
                .flatMap(list -> list.stream());
    }

    @Override
    public Optional<Method> find(String className, String methodName, String signature) {
        List<Method> poolList = pool.getOrDefault(className, new ArrayList<>());
        return findFromList(poolList, methodName, signature);
    }

    private boolean throwIfFound(List<Method> methods, Method method) {
        if(findFromList(methods, method.methodName(), method.signature()).isPresent())
            throw new MultipleDataException(method);
        return true;
    }

    private Optional<Method> findFromList(List<Method> methods, String methodName, String signature) {
        return methods.stream()
                .filter(m -> m.match(methodName, signature))
                .findFirst();
    }

}
