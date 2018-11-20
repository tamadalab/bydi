package jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class EuclideanDistanceCalculatorTest {
    private DistanceCalculator calculator;

    @Before
    public void setUp() {
        calculator = new EuclideanDistanceCalculator();
    }

    @Test
    public void testBasic() {
        assertThat(calculator.calculate(new int[] { 0, 0 }, new int[] { 3, 4 }),
                is(closeTo(5.0, 1E-5)));
    }

    @Test
    public void testBasic2() {
        assertThat(calculator.calculate(new int[] { 0, 4, 0 }, new int[] { 3, 0 }),
                is(closeTo(5.0, 1E-5)));
    }
}
