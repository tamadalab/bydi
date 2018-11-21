package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MethodInfoTest {
    private MethodInfo info = new MethodInfo("className", "methodName", "signature");

    @Test
    public void testBasic() {
        assertThat(info.className(), is("className"));
        assertThat(info.methodName(), is("methodName"));
        assertThat(info.signature(), is("signature"));
    }

    @Test
    public void testToString() {
        assertThat(info.toString(), is("className,methodName,signature"));
    }

    @Test
    public void testEquals() {
        MethodInfo info2 = new MethodInfo("className", "methodName", "signature");
        assertThat(info, is(info2));
        assertThat(info.hashCode(), is(info2.hashCode()));
        assertThat(info, is(not(new Object())));
        assertThat(info, is(not(new MethodInfo("className", "methodName", "XXXXX"))));
        assertThat(info, is(not(new MethodInfo("className", "XXXX", "signature"))));
        assertThat(info, is(not(new MethodInfo("XXXX", "methodName", "signature"))));
    }
}
