package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;

public class TranslatorBuilderTest {
    private TranslatorBuilder builder = new TranslatorBuilder();

    @Test
    public void testTranslatorBuilder() {
        assertThat(builder.get("proguard").get(), is(instanceOf(ProGuardTranslator.class)));
        assertThat(builder.get("yguard").get(), is(instanceOf(YGuardTranslator.class)));
        assertThat(builder.get("allatori").get(), is(instanceOf(AllatoriTranslator.class)));
        assertThat(builder.get("zkm").get(), is(instanceOf(ZKMTranslator.class)));
        assertThat(builder.get("unknown"), is(Optional.empty()));
    }
}
