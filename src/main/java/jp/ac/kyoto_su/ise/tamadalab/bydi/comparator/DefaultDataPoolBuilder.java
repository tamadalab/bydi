package jp.ac.kyoto_su.ise.tamadalab.bydi.comparator;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.OpcodeExtractor;
import jp.ac.kyoto_su.ise.tamadalab.bydi.parser.CsvDataParser;

public class DefaultDataPoolBuilder implements DataPoolBuilder {
    private CsvDataParser parser = new CsvDataParser();
    private OpcodeExtractor extractor = new OpcodeExtractor();

    @Override
    public Optional<DataPool> extract(String path) {
        System.out.printf("path %s%n", path);
        if(path.endsWith(".csv")) {
            return parser.extract(path);
        }
        else if(path.endsWith(".jar")) {
            return extractor.extract(path);
        }
        throw new UnknownFileFormatException(path + ": unknown file format.");
    }

}
