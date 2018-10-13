package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvData {
    private List<Data> list;

    CsvData(Stream<Data> stream){
        this.list = stream
                .collect(Collectors.toList());
    }

    public Optional<Data> remove(String className, String methodName, String parameters){
        Optional<Data> data = find(className, methodName, parameters);
        data.ifPresent(item -> list.remove(item));
        return data;
    }

    public Optional<Data> find(String className, String methodName, String parameters) {
        List<Data> targets = list.stream()
                .filter(d -> Objects.equals(d.getName(), className))
                .filter(d -> Objects.equals(d.getMethodName(),  methodName))
                .collect(Collectors.toList());
        if(targets.size() == 1)
            return Optional.of(targets.get(0));
        return targets.stream()
                .filter(d -> Objects.equals(d.getParameters(), parameters))
                .findFirst();
    }

    public Stream<Data> stream(){
        return list.stream();
    }
}
