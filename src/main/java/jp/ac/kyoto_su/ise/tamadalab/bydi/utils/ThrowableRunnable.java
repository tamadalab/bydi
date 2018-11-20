package jp.ac.kyoto_su.ise.tamadalab.bydi.utils;

@FunctionalInterface
public interface ThrowableRunnable {
    <T extends Throwable> void action() throws T;
}
