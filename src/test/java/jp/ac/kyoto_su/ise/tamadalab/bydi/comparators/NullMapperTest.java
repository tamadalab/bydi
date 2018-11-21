package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class NullMapperTest {
    private Mapper mapper = new NullMapper();

    public void testBasic() {
        MethodInfo info = new MethodInfo("className", "methodName", "signature");

        assertThat(mapper.map(info), is(info));
        assertThat(mapper.map("className", "methodName", "signature"), is(info));
    }
}
