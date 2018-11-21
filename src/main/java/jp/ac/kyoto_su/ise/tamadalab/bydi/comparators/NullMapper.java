package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class NullMapper implements Mapper {
    public Optional<MethodInfo> map(MethodInfo info) {
        return Optional.ofNullable(info);
    }
}
