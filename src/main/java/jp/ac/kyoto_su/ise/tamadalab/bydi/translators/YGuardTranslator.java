package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

public class YGuardTranslator extends ObfuscationToolTranslator {
    @Override
    protected Store construct() {
        return new YGuardStore();
    }
}