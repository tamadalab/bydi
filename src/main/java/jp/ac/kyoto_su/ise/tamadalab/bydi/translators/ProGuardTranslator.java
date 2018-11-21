package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class ProGuardTranslator implements Translator {
    @Override
    public void translate(Stream<String> stream, PrintWriter out) throws IOException {
        ProGuardStore store = new ProGuardStore();
        store.start();
        stream.forEach(line -> store.storeItem(line));
        store.done();
    }

}
