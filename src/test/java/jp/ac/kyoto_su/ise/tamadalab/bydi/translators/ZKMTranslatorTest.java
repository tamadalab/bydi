package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.stream.Stream;

import org.junit.Test;

public class ZKMTranslatorTest {
    private Translator translator = new ZKMTranslator();

    @Test
    public void testTranslate() throws Exception {
        URL location = getClass().getResource("/resources/sample.zkm.mapping");
        StringWriter out = new StringWriter();
        try(Stream<String> stream = new BufferedReader(new InputStreamReader(location.openStream())).lines()) {
            translator.translate(stream, new PrintWriter(out));
        }
        String[] lines = out.toString().split("\r?\n");

        assertThat(lines.length, is(2));

        String[] items1 = lines[0].split(",");
        assertThat(items1.length, is(6));
        assertThat(items1[0], is("jp.ac.kyoto_su.ise.tamadalab.bydi.SampleClass"));
        assertThat(items1[1], is("sampleMethod"));
        assertThat(items1[2], is("(Ljp/ac/kyoto_su/ise/tamadalab/bydi/SampleClass;I)V"));
        assertThat(items1[3], is("a"));
        assertThat(items1[4], is("b"));
        assertThat(items1[5], is("(La;I)V"));

        String[] items2 = lines[1].split(",");
        assertThat(items2.length, is(6));
        assertThat(items2[0], is("jp.ac.kyoto_su.ise.tamadalab.bydi.SampleClass"));
        assertThat(items2[1], is("sample"));
        assertThat(items2[2], is("(Ljp/ac/kyoto_su/ise/tamadalab/bydi/SampleClass;I)V"));
        assertThat(items2[3], is("a"));
        assertThat(items2[4], is("sample"));
        assertThat(items2[5], is("(La;I)V"));
    }
}
