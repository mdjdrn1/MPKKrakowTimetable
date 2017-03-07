package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.CracowParser;
import com.github.mdjdrn1.MPKKrakowTimetable.IParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CracowParserTest
{
    @Test
    void linesNumbersListShouldHave5thItemEqual5() throws Exception
    {
        IParser parser = new CracowParser();
        List<Integer> lines = parser.getLinesNumbersList();
        assertThat(lines.get(4)).isEqualTo(5);
    }

    @Test
    void linesNumbersListShouldContain253() throws Exception
    {
        IParser parser = new CracowParser();
        List<Integer> lines = parser.getLinesNumbersList();
        assertThat(lines.contains(253));
    }
}
