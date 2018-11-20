package jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ManhattantDistanceCalculatorTest {
    private DistanceCalculator calculator;

    @Before
    public void setUp() {
        calculator = new ManhattanDistanceCalculator();
    }

    @Test
    public void testBasic() {
        assertThat(calculator.calculate(new int[] { 0, 3, 0 }, new int[] { 2, 0, 4 }),
                is(closeTo(9.0, 1E-5)));
    }

    @Test
    public void testDifferentLengthOfSource() {
        assertThat(calculator.calculate(new int[] { 0, 3, 0, 5 }, new int[] { 2, 0, 4 }),
                is(closeTo(14.0, 1E-5)));
    }
}
