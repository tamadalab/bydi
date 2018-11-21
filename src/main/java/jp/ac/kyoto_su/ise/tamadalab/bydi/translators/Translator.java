package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public interface Translator {
    void translate(Stream<String> stream, PrintWriter out) throws IOException;
}
