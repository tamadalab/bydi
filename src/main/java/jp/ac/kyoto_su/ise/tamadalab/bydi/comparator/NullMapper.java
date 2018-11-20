package jp.ac.kyoto_su.ise.tamadalab.bydi.comparator;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class NullMapper implements Mapper {
    public MethodInfo map(MethodInfo info) {
        return info;
    }
}
