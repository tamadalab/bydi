package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class MethodInfoPair extends Pair<MethodInfo, MethodInfo> {
    public MethodInfoPair(MethodInfo before, MethodInfo after) {
        super(before, after);
    }
}
