package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.CracowParser;
import com.github.mdjdrn1.MPKKrakowTimetable.IParser;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;
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
    void shouldReturnValidLine52URL() throws Exception
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
    void directionsListLine451() throws Exception
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

    @Test
    void stopsListLine116() throws Exception
    {
        IParser parser = new CracowParser();

        List<Stop> expectedStops = new ArrayList<>(
                Arrays.asList(
                        new Stop("Czerwone Maki P+R", 1),
                        new Stop("Czerwone Maki P+R", 2),
                        new Stop("Mochnaniec", 3),
                        new Stop("Skotniki Szkoła", 4),
                        new Stop("Brücknera", 5),
                        new Stop("Skotniki", 6),
                        new Stop("Orszy-Broniewskiego", 7)
                )
        );

        Direction direction = parser.getDirectionsList(116).get(0);
        List<Stop> actualStops = parser.getStopsList(116, direction);

        assertThat(actualStops).isEqualTo(expectedStops);
    }

    @Test
    void stopsListLine100() throws Exception
    {
        IParser parser = new CracowParser();

        List<Stop> expectedStops = new ArrayList<>(
                Arrays.asList(
                        new Stop("Salwator", 1),
                        new Stop("Malczewskiego", 2),
                        new Stop("Aleja Waszyngtona", 3)
                )
        );

        Direction direction = parser.getDirectionsList(100).get(0);
        List<Stop> actualStops = parser.getStopsList(100, direction);

        assertThat(actualStops).isEqualTo(expectedStops);
    }

    @Test
    void timetableLine213() throws Exception
    {
        IParser parser = new CracowParser();

        ArrayList<ArrayList<List<String>>> expectedTimetable = new ArrayList<>(3);
        System.out.printf("expectedTimetable.size() " + expectedTimetable.size());

        while(expectedTimetable.size() < 3)
            expectedTimetable.add(new ArrayList<List<String>>(24));

        for(int i = 0; i < expectedTimetable.size(); ++i)
        {
            while(expectedTimetable.get(i).size() < 24)
                expectedTimetable.get(i).add(new ArrayList<String>());
        }

        expectedTimetable.get(0).set(4, Arrays.asList("00", "50"));
        expectedTimetable.get(0).set(6, Arrays.asList("10"));
        expectedTimetable.get(0).set(7, Arrays.asList("10"));
        expectedTimetable.get(0).set(8, Arrays.asList("20"));
        expectedTimetable.get(0).set(10, Arrays.asList("05"));
        expectedTimetable.get(0).set(12, Arrays.asList("30"));
        expectedTimetable.get(0).set(14, Arrays.asList("10", "45"));
        expectedTimetable.get(0).set(15, Arrays.asList("50"));
        expectedTimetable.get(0).set(16, Arrays.asList("45"));
        expectedTimetable.get(0).set(17, Arrays.asList("30"));
        expectedTimetable.get(0).set(18, Arrays.asList("35"));
        expectedTimetable.get(0).set(19, Arrays.asList("30"));
        expectedTimetable.get(0).set(22, Arrays.asList("25"));

        expectedTimetable.get(1).set(4, Arrays.asList("20"));
        expectedTimetable.get(1).set(6, Arrays.asList("00"));
        expectedTimetable.get(1).set(20, Arrays.asList("25"));
        expectedTimetable.get(1).set(20, Arrays.asList("05"));

        expectedTimetable.get(2).set(4, Arrays.asList("20"));
        expectedTimetable.get(2).set(6, Arrays.asList("00"));
        expectedTimetable.get(2).set(20, Arrays.asList("25"));
        expectedTimetable.get(2).set(20, Arrays.asList("05"));

        Direction direction = parser.getDirectionsList(113).get(0);
        Stop stop = parser.getStopsList(113, direction).get(0);
        ArrayList<ArrayList<List<String>>> actualTimetable = parser.getTimetable(100, direction, stop);

        assertThat(actualTimetable).isEqualTo(expectedTimetable);
    }
}
