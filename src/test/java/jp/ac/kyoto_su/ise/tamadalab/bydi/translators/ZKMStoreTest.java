package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class ZKMStoreTest {
    private ZKMStore store = new ZKMStore();

    @Test
    public void testStripModifiers() {
        assertThat(store.stripModifiers("public final org.apache.commons.io.ByteOrderParser =>  an"),
                is("org.apache.commons.io.ByteOrderParser =>  an"));
        assertThat(store.stripModifiers("public void <init>() SignatureNotChanged"),
                is("void <init>() SignatureNotChanged"));
        assertThat(store.stripModifiers("private static final long serialVersionUID NameNotChanged"),
                is("long serialVersionUID NameNotChanged"));
        assertThat(store.stripModifiers("private final java.util.List fileFilters   =>  a"),
                is("java.util.List fileFilters   =>  a"));
        assertThat(store.stripModifiers("private static void innerListFiles(java.util.Collection, java.io.File, org.apache.commons.io.filefilter.IOFileFilter, boolean) => a(java.util.Collection, java.io.File, a_, boolean)"),
                is("void innerListFiles(java.util.Collection, java.io.File, org.apache.commons.io.filefilter.IOFileFilter, boolean) => a(java.util.Collection, java.io.File, a_, boolean)"));
        assertThat(store.stripModifiers("hogehogehoge aaa"),
                is("hogehogehoge aaa"));
    }
}
