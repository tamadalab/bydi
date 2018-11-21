package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PairTest {
    private Pair<String, Integer> pair = new Pair<>("string", 1);

    @Test
    public void testBasic() {
        assertThat(pair, is(new Pair<>("string", 1)));
    }

    @Test
    public void testMap() {
        assertThat(pair.map((left, right) -> left), is("string"));
        assertThat(pair.map((left, right) -> right), is(1));
    }
    
    @Test
    public void testConsume() {
        pair.perform((left, right) -> assertTrue(true));
    }

    @Test
    public void testPredicate() {
        assertThat(pair.test((left, right) -> right == 1), is(true));
    }

    @Test
    public void testEquals() {
        assertThat(pair.hashCode(), is(new Pair<>("string", 1).hashCode()));
        assertThat(pair, is(not(new Object())));
        assertThat(pair, is(not(new Pair<>("string", 10.0))));
        assertThat(pair, is(not(new Pair<>("string2", 1))));
        assertThat(pair, is(not(new Pair<>("string", 10))));
    }
}
