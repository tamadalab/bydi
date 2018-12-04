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

public class ProGuardTranslatorTest {
    private ProGuardTranslator translator = new ProGuardTranslator();

    @Test
    public void testTranslate() throws Exception {
        URL location = getClass().getResource("/resources/sample.proguard.mapping");
        StringWriter out = new StringWriter();
        try(Stream<String> stream = new BufferedReader(new InputStreamReader(location.openStream())).lines()) {
            translator.translate(stream, new PrintWriter(out));
        }
        String[] lines = out.toString().split("\r?\n");

        assertThat(lines.length, is(1));
        String[] items = lines[0].split(",");

        assertThat(items.length, is(6));
        assertThat(items[0], is("jp.ac.kyoto_su.ise.tamadalab.bydi.SampleClass"));
        assertThat(items[1], is("sampleMethod"));
        assertThat(items[2], is("(Ljp/ac/kyoto_su/ise/tamadalab/bydi/SampleClass;I)V"));
        assertThat(items[3], is("jp.ac.kyoto_su.ise.tamadalab.bydi.a"));
        assertThat(items[4], is("b"));
        assertThat(items[5], is("(Ljp/ac/kyoto_su/ise/tamadalab/bydi/a;I)V"));
    }
}
