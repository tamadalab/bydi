package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

public class ZKMTranslator extends ObfuscationToolTranslator {
    @Override
    protected Store construct() {
        return new ZKMStore();
    }
}
