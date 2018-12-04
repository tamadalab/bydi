package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class NamePair extends Pair<String, String> {
    public NamePair(String before, String after) {
        super(before, after);
    }

    public String toString() {
        return map((before, after) -> String.format("%s -> %s", before, after));
    }
}
