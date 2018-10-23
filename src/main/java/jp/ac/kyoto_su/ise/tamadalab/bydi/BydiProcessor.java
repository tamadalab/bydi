package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;

public interface BydiProcessor {
    Optional<DataPool> extract(String path);
}
