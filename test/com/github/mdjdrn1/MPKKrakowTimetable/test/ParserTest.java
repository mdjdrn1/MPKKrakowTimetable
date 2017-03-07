package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.FakeParser;
import com.github.mdjdrn1.MPKKrakowTimetable.IParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ParserTest
{
    @Test
    void shouldReturnLineNumberEqual5() throws Exception
    {
        IParser parser = new FakeParser();
        List<Integer> lines = parser.getLineNumberList();
        Assertions.assertEquals((Integer) 5, lines.get(4));
    }
}
