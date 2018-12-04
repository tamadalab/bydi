package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms.DistanceCalculator;
import jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms.DistanceCalculatorBuilder;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Processor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.DataPool;

public class ComparisonProcessor implements Processor {

    @Override
    public Arguments buildArguments() {
        return new ComparisonArguments();
    }

    @Override
    public void perform(Arguments args) {
        List<DataPool> pools = readData(args);
        Mapper mapper = constructMapper(args);
        DistanceCalculator calculator = buildDistanceCalculator(args);
        printAll(pools, calculator, mapper);
    }

    private Mapper constructMapper(Arguments args) {
        return new MapperBuilder()
                .build(args.get("mapper"), args.get("format", "null"));
    }

    private void printAll(List<DataPool> pools, DistanceCalculator calculator, Mapper mapper) {
        printAll(pools.get(0), pools.get(1), calculator, mapper);
    }

    private void printAll(DataPool original, DataPool obfuscated, DistanceCalculator calculator, Mapper mapper) {
        original.stream()
        .forEach(data -> calculateAndPrint(data, obfuscated, calculator, mapper));        
    }

    private void calculateAndPrint(Method data1, DataPool obfuscated,
            DistanceCalculator calculator, Mapper mapper) {
        Optional<Method> data2 = mapper.find(data1.info(), obfuscated);
        data2.ifPresentOrElse(d2 -> print(data1, d2, calculator.calculate(data1.opcodes(), d2.opcodes())),
                () -> printNotFound(data1));
    }

    private void printNotFound(Method data) {
        System.out.printf("%s,%s,%s,,,,%d,,,not found%n", data.className(), data.methodName(),
                data.signature(), data.opcodes().length);
    }

    private void print(Method data1, Method data2, double distance) {
        System.out.printf("%s,%s,%s,%s,%s,%s,%d,%d,%g%n", data1.className(), data1.methodName(), 
                data1.signature(), data2.className(), data2.methodName(), data2.signature(),
                data1.opcodes().length, data2.opcodes().length,
                distance);
    }
    private DistanceCalculator buildDistanceCalculator(Arguments args) {
        DistanceCalculator calculator = new DistanceCalculatorBuilder()
                .build(args.get("algorithm", "edit"));
        if(calculator == null)
            throw new UnknownAlgorithmException(args.get("algorithm", "edit"));
        return calculator;
    }

    private List<DataPool> readData(Arguments args) {
        DataPoolBuilder processor = new DefaultDataPoolBuilder();
        return args.stream()
                .map(arg -> processor.extract(arg))
                .filter(pool -> pool.isPresent())
                .map(item -> item.get())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ComparisonProcessor processor = new ComparisonProcessor();
        processor.run(args);
    }
}
