package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

public interface Store {
    default void start() {
    }

    default void storeItem(String line) {
        storeItem(line, false);
    }

    void storeItem(String line, boolean memberFlag);

    void done();
}
