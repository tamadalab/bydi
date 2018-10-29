package jp.ac.kyoto_su.ise.tamadalab.bydi.comparator;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;

public interface DataPoolBuilder {
    Optional<DataPool> extract(String path);
}
