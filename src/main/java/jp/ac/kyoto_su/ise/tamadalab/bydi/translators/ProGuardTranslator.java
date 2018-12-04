package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

public class ProGuardTranslator extends ObfuscationToolTranslator {

    @Override
    protected Store construct() {
        return new ProGuardStore();
    }
}
