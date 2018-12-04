package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

public class AllatoriTranslator extends ObfuscationToolTranslator {
    @Override
    protected Store construct() {
        return new AllatoriStore();
    }
}
