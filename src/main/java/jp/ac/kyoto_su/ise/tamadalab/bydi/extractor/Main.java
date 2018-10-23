package jp.ac.kyoto_su.ise.tamadalab.bydi.extractor;

import java.util.Arrays;
import java.util.Optional;

public class Main {
    public void run(String[] args) {
        OpcodeExtractor extractor = new OpcodeExtractor();
        Arrays.stream(args)
        .map(arg -> extractor.extract(arg))
        .forEach(pool -> printPool(pool));
    }

    private void printPool(Optional<DataPool> pool) {
        pool.ifPresent(store -> printAll(store));
    }

    private void printAll(DataPool pool) {
        pool.stream()
        .map(m -> String.format("%s,%s,%s,%d,%s",
                m.className(), m.methodName(), m.signature(), m.opcodeLength(), m.opcodesString()))
        .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Main extractor = new Main();
        extractor.run(args);
    }
}
