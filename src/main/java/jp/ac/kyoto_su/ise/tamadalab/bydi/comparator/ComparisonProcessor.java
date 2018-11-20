package jp.ac.kyoto_su.ise.tamadalab.bydi.comparator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms.DistanceCalculator;
import jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms.DistanceCalculatorBuilder;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Processor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;

public class ComparisonProcessor implements Processor {

    @Override
    public Arguments buildArguments() {
        return new ComparisonArguments();
    }

    @Override
    public void perform(Arguments args) {
        List<DataPool> pools = readData(args);
        DistanceCalculator calculator = buildDistanceCalculator(args);
        printAll(pools, calculator);
    }

    private void printAll(List<DataPool> pools, DistanceCalculator calculator) {
        printAll(pools.get(0), pools.get(1), calculator);
    }

    private void printAll(DataPool original, DataPool obfuscated, DistanceCalculator calculator) {
        original.stream()
        .forEach(data -> calculateAndPrint(data, obfuscated, calculator));        
    }

    private void calculateAndPrint(Method data1, DataPool obfuscated, DistanceCalculator calculator) {
        Optional<Method> data2 = obfuscated.find(data1.className(), data1.methodName(), data1.signature());
        data2.ifPresentOrElse(d2 -> print(data1, d2, calculator.calculate(data1.opcodes(), d2.opcodes())),
                () -> printNotFound(data1));
    }

    private void printNotFound(Method data) {
        System.out.printf("%s,%s,%s,%d,,,not found%n", data.className(), data.methodName(),
                data.signature(), data.opcodes().length);
    }

    private void print(Method data1, Method data2, double distance) {
        System.out.printf("%s,%s,%s,%d,%d,%g%n", data1.className(), data1.methodName(), 
                data1.signature(), data1.opcodes().length, data2.opcodes().length,
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

}
