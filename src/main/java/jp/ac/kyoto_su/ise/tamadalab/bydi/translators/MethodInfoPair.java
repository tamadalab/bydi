package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

import java.util.Objects;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class MethodInfoPair extends Pair<MethodInfo, MethodInfo> {
    public MethodInfoPair(MethodInfo before, MethodInfo after) {
        super(before, after);
    }

    public boolean matchBefore(MethodInfo info) {
        return test((before, after) -> Objects.equals(before, info));
    }

    public boolean matchAfter(MethodInfo info) {
        return test((before, after) -> Objects.equals(after, info));
    }
}
