package jp.ac.kyoto_su.ise.tamadalab.bydi.extractors;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Processor;

public class ExtractionProcessor implements Processor {
    public Arguments buildArguments() {
        return new ExtractionArguments();
    }

    @Override
    public void perform(Arguments args) {
        OpcodeExtractor extractor = new OpcodeExtractor();
        args.stream()
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

    public static void main(String[] args) throws Exception {
        ExtractionProcessor extractor = new ExtractionProcessor();
        extractor.perform(extractor.parse(args));
    }
}
