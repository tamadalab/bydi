package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.OpcodeExtractor;

public class DefaultBydiProcessor implements BydiProcessor {
    private CsvDataParser parser = new CsvDataParser();
    private OpcodeExtractor extractor = new OpcodeExtractor();

    @Override
    public Optional<DataPool> extract(String path) {
        if(path.endsWith(".csv")) {
            return parser.extract(path);
        }
        else if(path.endsWith(".jar")) {
            return extractor.extract(path);
        }
        throw new UnknownFileFormatException(path + ": unknown file format.");
    }

}
