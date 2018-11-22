package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class NullStore implements Store {
    @Override
    public void storeItem(String line, boolean memberFlag) {
        // do nothing.
    }

    @Override
    public void done() {
        // do nothing.
    }
}
