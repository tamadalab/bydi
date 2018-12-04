package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class AllatoriStoreTest {
    private AllatoriStore store = new AllatoriStore();

    @Test
    public void testStripModifiers() {
        Pair<String, String> pair1 = store.parseNamePair("<class old=\"org.apache.commons.io.output.WriterOutputStream\" new=\"a\">");
        assertThat(pair1.map((b, a) -> b), is("org.apache.commons.io.output.WriterOutputStream"));
        assertThat(pair1.map((b, a) -> a), is("a"));

        Pair<String, String> pair2 = store.parseNamePair("<class new=\"b\" old=\"org.apache.commons.io.output.WriterOutputStream\">");
        assertThat(pair2.map((b, a) -> b), is("org.apache.commons.io.output.WriterOutputStream"));
        assertThat(pair2.map((b, a) -> a), is("b"));
    }
}
