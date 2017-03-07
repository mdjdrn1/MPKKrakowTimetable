package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.CracowParser;
import com.github.mdjdrn1.MPKKrakowTimetable.IParser;
import com.github.mdjdrn1.MPKKrakowTimetable.stuctures.Direction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    void linesNumbersListShouldContain18() throws Exception
    {
        IParser parser = new CracowParser();
        List<Integer> lines = parser.getLinesNumbersList();
        assertThat(lines.contains(18));
    }

    @Test
    void shouldReturnValidLine52Number() throws Exception
    {
        CracowParser parser = new CracowParser();

        assertThat(parser.getLineUrl(52)).matches(".*lang=(PL|EN|DE)&rozklad=\\d{8}&linia=52$");
    }

    @Test
    void directionsListFor116() throws Exception
    {

        IParser parser = new CracowParser();

        List<Direction> expectedDirections = new ArrayList<>(
                Arrays.asList(
                        new Direction("Kozienicka", 1),
                        new Direction("Czerwone Maki P+R", 2)
                )
        );
        List<Direction> actualDirections = parser.getDirectionsList(116);
        assertThat(actualDirections).isEqualTo(expectedDirections);
    }


    @Test
    void directionsListFor451() throws Exception
    {

        IParser parser = new CracowParser();

        List<Direction> expectedDirections = new ArrayList<>(
                Arrays.asList(
                        new Direction("Judyma Szkoła", 1)
                )
        );
        List<Direction> actualDirections = parser.getDirectionsList(451);
        assertThat(actualDirections).isEqualTo(expectedDirections);
    }
}
