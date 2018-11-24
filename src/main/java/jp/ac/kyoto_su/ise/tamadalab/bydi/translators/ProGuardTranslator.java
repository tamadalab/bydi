package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class ProGuardTranslator implements Translator {
    @Override
    public void translate(Stream<String> stream, PrintWriter out) throws IOException {
        Store store = read(stream);
        store.stream().forEach(pair -> ship(out, pair));
    }

    private void ship(PrintWriter out, MethodInfoPair pair) {
        String result = pair.map((before, after) -> String.format("%s,%s", before, after));
        out.println(result);
    }

    private Store read(Stream<String> stream) {
        Store store = new ProGuardStore();
        store.start();
        stream.forEach(line -> store.storeItem(line));
        store.done();
        return store;
    }

}
