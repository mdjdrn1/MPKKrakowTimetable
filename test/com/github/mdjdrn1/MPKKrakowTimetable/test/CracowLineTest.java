package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.CracowLine;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CracowLineTest
{
    @Test
    void shouldThrowExceptionForNonExistingLine() throws Exception
    {
        assertThatThrownBy(() -> new CracowLine(10000))
                .isInstanceOf(Exception.class)
                .hasMessage("Line " + 10000 + " doesn't exist.");
    }

    @Test
    void linesNumbersListShouldHave5thItemEqual5() throws Exception
    {
        List<Integer> lines = CracowLine.getLineNumbersList();
        assertThat(lines.get(4)).isEqualTo(5);
    }

    @Test
    void linesNumbersListShouldContain18() throws Exception
    {
        List<Integer> lines = CracowLine.getLineNumbersList();
        assertThat(lines.contains(18));
    }

    @Test
    void directionsListFor116() throws Exception
    {
        List<Direction> expectedDirections = new ArrayList<>(
                Arrays.asList(
                        new Direction("Kozienicka", 1),
                        new Direction("Czerwone Maki P+R", 2)
                )
        );

        CracowLine line = new CracowLine(116);
        List<Direction> actualDirections = line.getDirectionsList();
        assertThat(actualDirections).isEqualTo(expectedDirections);
    }

    @Test
    void directionsListLine451() throws Exception
    {
        List<Direction> expectedDirections = new ArrayList<>(
                Arrays.asList(
                        new Direction("Judyma Szkoła", 1)
                )
        );

        CracowLine line = new CracowLine(451);
        List<Direction> actualDirections = line.getDirectionsList();
        assertThat(actualDirections).isEqualTo(expectedDirections);
    }

    @Test
    void stopsListLine116() throws Exception
    {
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

        CracowLine line = new CracowLine(116);
        Direction direction = line.getDirectionsList().get(0);
        List<Stop> actualStops = line.getStopsList(direction);

        assertThat(actualStops).isEqualTo(expectedStops);
    }

    @Test
    void stopsListLine100() throws Exception
    {
        List<Stop> expectedStops = new ArrayList<>(
                Arrays.asList(
                        new Stop("Salwator", 1),
                        new Stop("Malczewskiego", 2),
                        new Stop("Aleja Waszyngtona", 3)
                )
        );

        CracowLine line = new CracowLine(100);
        Direction direction = line.getDirectionsList().get(0);
        List<Stop> actualStops = line.getStopsList(direction);

        assertThat(actualStops).isEqualTo(expectedStops);
    }

    @Test
    void timetableDayLine213() throws Exception
    {
        // TODO: run test only if timetable didn't change (assume?)

        ArrayList<ArrayList<List<String>>> expectedTimetable = new ArrayList<>(3);

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
        expectedTimetable.get(1).set(22, Arrays.asList("05"));

        expectedTimetable.get(2).set(4, Arrays.asList("20"));
        expectedTimetable.get(2).set(6, Arrays.asList("00"));
        expectedTimetable.get(2).set(20, Arrays.asList("25"));
        expectedTimetable.get(2).set(22, Arrays.asList("05"));

        CracowLine line = new CracowLine(213);
        Direction direction = line.getDirectionsList().get(0);
        Stop stop = line.getStopsList(direction).get(0);
        ArrayList<ArrayList<List<String>>> actualTimetable = line.getTimetable(direction, stop);

        assertThat(actualTimetable).isEqualTo(expectedTimetable);
    }

    @Test
    void timetableNightLine605() throws Exception
    {
        ArrayList<ArrayList<List<String>>> expectedTimetable = new ArrayList<>(3);

        while(expectedTimetable.size() < 3)
            expectedTimetable.add(new ArrayList<List<String>>(24));

        for(int i = 0; i < expectedTimetable.size(); ++i)
        {
            while(expectedTimetable.get(i).size() < 24)
                expectedTimetable.get(i).add(new ArrayList<String>());
        }

        expectedTimetable.get(0).set(23, Arrays.asList("31A"));
        expectedTimetable.get(0).set(0, Arrays.asList("31A"));
        expectedTimetable.get(0).set(1, Arrays.asList("31A"));
        expectedTimetable.get(0).set(2, Arrays.asList("31A"));
        expectedTimetable.get(0).set(3, Arrays.asList("31A"));

        expectedTimetable.get(1).set(23, Arrays.asList("31A"));
        expectedTimetable.get(1).set(0, Arrays.asList("31"));
        expectedTimetable.get(1).set(1, Arrays.asList("31"));
        expectedTimetable.get(1).set(2, Arrays.asList("31A"));
        expectedTimetable.get(1).set(3, Arrays.asList("31A"));

        expectedTimetable.get(2).set(23, Arrays.asList("31A"));
        expectedTimetable.get(2).set(0, Arrays.asList("31A"));
        expectedTimetable.get(2).set(1, Arrays.asList("31A"));
        expectedTimetable.get(2).set(2, Arrays.asList("31A"));
        expectedTimetable.get(2).set(3, Arrays.asList("31A"));

        CracowLine line = new CracowLine(605);
        Direction direction = line.getDirectionsList().get(0);
        Stop stop = line.getStopsList(direction).get(0);
        ArrayList<ArrayList<List<String>>> actualTimetable = line.getTimetable(direction, stop);

        assertThat(actualTimetable).isEqualTo(expectedTimetable);
    }
}
