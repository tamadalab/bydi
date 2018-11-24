package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.stream.Stream;

public class NullStore implements Store {
    @Override
    public void storeItem(String line, boolean memberFlag) {
        // do nothing.
    }

    @Override
    public void done() {
        // do nothing.
    }

    @Override
    public Stream<MethodInfoPair> stream() {
        return Stream.empty();
    }
}
