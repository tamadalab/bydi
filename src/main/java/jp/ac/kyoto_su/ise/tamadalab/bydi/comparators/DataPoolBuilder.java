package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.DataPool;

public interface DataPoolBuilder {
    Optional<DataPool> extract(String path);
}
