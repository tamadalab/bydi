package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public abstract class ObfuscationToolTranslator implements Translator {
    @Override
    public void translate(Stream<String> stream, PrintWriter out) throws IOException {
        Store store = read(stream);
        store.stream().forEach(pair -> ship(out, pair));
    }

    private void ship(PrintWriter out, MethodInfoPair pair) {
        String result = pair.map((before, after) -> String.format("%s,%s", before, after));
        out.println(result);
    }

    protected abstract Store construct();

    private Store read(Stream<String> stream) {
        Store store = construct();
        store.start();
        stream.forEach(line -> store.storeItem(line));
        store.done();
        return store;
    }

}
