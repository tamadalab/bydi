package jp.ac.kyoto_su.ise.tamadalab.bydi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class EditDistanceCalculatorTest {
    private EditDistanceCalculator calculator;

    private int[] stringToIntArray(String name) {
        return IntStream.range(0, name.length())
                .map(index -> (int)name.charAt(index))
                .toArray();
    }

    @Before
    public void setUp() {
        calculator = new EditDistanceCalculator();
    }

    @Test
    public void testBasic() {
        assertThat(calculator.calculate(stringToIntArray("other"), stringToIntArray("mother")), is(1));
    }

    @Test
    public void testStringToIntArrayMethod() {
        int[] array = stringToIntArray("other");
        assertThat(array.length, is(5));
        assertThat(array[0], is((int)'o'));
        assertThat(array[1], is((int)'t'));
        assertThat(array[2], is((int)'h'));
        assertThat(array[3], is((int)'e'));
        assertThat(array[4], is((int)'r'));
    }
}
