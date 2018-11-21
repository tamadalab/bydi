package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

public class MethodTest {
    private Method method = new Method(
            new MethodInfo("className", "methodName", "signature"),
            Optional.of(new int[] { 0, 1, 2, }));

    @Test
    public void testBasic() {
        assertThat(method.info(), is(new MethodInfo("className", "methodName", "signature")));
        assertThat(method.className(), is("className"));
        assertThat(method.methodName(), is("methodName"));
        assertThat(method.signature(), is("signature"));
        assertThat(method.opcodeLength(), is(3));
        assertThat(method.opcodesString(), is("000102"));
        assertThat(wrap(method.opcodes()), is(arrayContaining(0, 1, 2)));
        assertThat(method.toString(), is("className,methodName,signature,3,000102"));
    }

    @Test
    public void testEmptyOpcode() {
        Method method2 = new Method(new MethodInfo("className", "methodName", "signature"),
                Optional.empty());
        assertThat(method2.opcodeLength(), is(0));
        assertThat(method2.opcodes(), is(new int[0]));
        assertThat(method2.match(method2), is(true));
        assertThat(method2.match(method2.info()), is(true));
    }

    private Integer[] wrap(int[] array) {
        return Arrays.stream(array)
                .mapToObj(Integer::valueOf)
                .toArray(Integer[]::new);
    }
}
