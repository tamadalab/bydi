package jp.ac.kyoto_su.ise.tamadalab.bydi.utils;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class TypeUtilsTest{
    @Test
    public void testBasic(){
        assertThat(TypeUtils.toDescriptor("boolean"),   is("Z"));
        assertThat(TypeUtils.toDescriptor("byte"),      is("B"));
        assertThat(TypeUtils.toDescriptor("char"),      is("C"));
        assertThat(TypeUtils.toDescriptor("short"),     is("S"));
        assertThat(TypeUtils.toDescriptor("int"),       is("I"));
        assertThat(TypeUtils.toDescriptor("long"),      is("J"));
        assertThat(TypeUtils.toDescriptor("float"),     is("F"));
        assertThat(TypeUtils.toDescriptor("double"),    is("D"));
        assertThat(TypeUtils.toDescriptor("hoge.TypeUtils"), is("Lhoge/TypeUtils;"));
    }

    @Test
    public void testArray(){
        assertThat(TypeUtils.toDescriptor("int[][]"), is("[[I"));
        assertThat(TypeUtils.toDescriptor("Test[]"), is("[LTest;"));
    }

    @Test
    public void testSignature(){
        assertThat(TypeUtils.toDescriptor("int", new String[] { "long", "int" }), is("(JI)I"));
    }
}
