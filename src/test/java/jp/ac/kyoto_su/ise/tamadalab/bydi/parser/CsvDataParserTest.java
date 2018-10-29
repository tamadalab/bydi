package jp.ac.kyoto_su.ise.tamadalab.bydi.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.ac.kyoto_su.ise.tamadalab.bydi.parser.CsvDataParser;

public class CsvDataParserTest {
    private CsvDataParser parser = new CsvDataParser();

    @Test
    public void testIntParse() {
        int[] data = parser.bytecode("1901b6c0b1");

        assertThat(data.length, is(5));
        assertThat(data[0], is(0x19));
        assertThat(data[1], is(0x01));
        assertThat(data[2], is(0xb6));
        assertThat(data[3], is(0xc0));
        assertThat(data[4], is(0xb1));
    }
}
