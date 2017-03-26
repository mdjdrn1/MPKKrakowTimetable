package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CracowLineTest
{
    public static class lineNumberTest
    {
        @Test
        void shouldThrowExceptionForNonExistingLine()
        {
            assertThatThrownBy(() -> new CracowLine(0))
                    .isInstanceOf(Exception.class)
                    .hasMessage("Line " + 0 + " doesn't exist.");
        }

        @Test
        void linesNumbersListShouldHave5thItemEqual5() throws ParsingException, ConnectionError
        {
            CracowLine line = new CracowLine(1);
            List<Integer> lines = line.getLineNumbersList();
            assertThat(lines.get(4)).isEqualTo(5);
        }

        @Test
        void linesNumbersListShouldContain18() throws ParsingException, ConnectionError
        {
            CracowLine line = new CracowLine(1);
            List<Integer> lines = line.getLineNumbersList();
            assertThat(lines.contains(18));
        }

    }

    public static class directionTest
    {
        @Test
        void directionsListFor116() throws ConnectionError, ParsingException
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
        void directionsListLine451() throws ConnectionError, ParsingException
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
    }

    public static class stopTest
    {
        @Test
        void stopsListLine116() throws ConnectionError, ParsingException
        {
            List<Stop> expectedStops = new ArrayList<>(
                    Arrays.asList(
                            new Stop("Czerwone Maki P+R", 1),
                            new Stop("Czerwone Maki P+R", 2),
                            new Stop("Mochnaniec", 3, true),
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
        void stopsListLine100() throws ConnectionError, ParsingException
        {
            List<Stop> expectedStops = new ArrayList<>(
                    Arrays.asList(
                            new Stop("Salwator", 1),
                            new Stop("Malczewskiego", 2, true),
                            new Stop("Aleja Waszyngtona", 3, true)
                    )
            );

            CracowLine line = new CracowLine(100);
            Direction direction = line.getDirectionsList().get(0);
            List<Stop> actualStops = line.getStopsList(direction);

            assertThat(actualStops).isEqualTo(expectedStops);
        }

        @Test
        void stopDelayListFor100() throws ConnectionError, ParsingException
        {
            CracowLine line = new CracowLine(100);
            Direction direction = line.getDirectionsList().get(0);

            List<Integer> expectedDelayList = new ArrayList<>(Arrays.asList(0, 2, 4));
            List<Integer> actualDelayList = line.getDelayList(direction);

            assertThat(expectedDelayList).isEqualTo(actualDelayList);
        }
    }


    public static class timetableTest
    {
        @Test
        void timetableDayLine213() throws ConnectionError, ParsingException
        {
            // TODO: should I run test only if timetable didn't change? (use assume?)

            ArrayList<Timetable> expectedTimetable = new ArrayList<>(3);

            while (expectedTimetable.size() < 3)
                expectedTimetable.add(new Timetable());

            expectedTimetable.get(0).setDescription("Weekday");
            expectedTimetable.get(0).addMinutes(4, Arrays.asList("00", "50"));
            expectedTimetable.get(0).addMinutes(6, "10");
            expectedTimetable.get(0).addMinutes(7, "10");
            expectedTimetable.get(0).addMinutes(8, "20");
            expectedTimetable.get(0).addMinutes(10, "05");
            expectedTimetable.get(0).addMinutes(12, "30");
            expectedTimetable.get(0).addMinutes(14, Arrays.asList("10", "45"));
            expectedTimetable.get(0).addMinutes(15, "50");
            expectedTimetable.get(0).addMinutes(16, "45");
            expectedTimetable.get(0).addMinutes(17, "30");
            expectedTimetable.get(0).addMinutes(18, "35");
            expectedTimetable.get(0).addMinutes(19, "30");
            expectedTimetable.get(0).addMinutes(22, "25");

            expectedTimetable.get(1).setDescription("Saturdays");
            expectedTimetable.get(1).addMinutes(4, "20");
            expectedTimetable.get(1).addMinutes(6, "00");
            expectedTimetable.get(1).addMinutes(20, "25");
            expectedTimetable.get(1).addMinutes(22, "05");

            expectedTimetable.get(2).setDescription("Holidays");
            expectedTimetable.get(2).addMinutes(4, "20");
            expectedTimetable.get(2).addMinutes(6, "00");
            expectedTimetable.get(2).addMinutes(20, "25");
            expectedTimetable.get(2).addMinutes(22, "05");

            CracowLine line = new CracowLine(213);
            Direction direction = line.getDirectionsList().get(0);
            Stop stop = line.getStopsList(direction).get(0);
            List<Timetable> actualTimetable = line.getTimetables(direction, stop);

            assertThat(actualTimetable).isEqualTo(expectedTimetable);
        }

        @Test
        void timetableNightLine605() throws ConnectionError, ParsingException
        {
            ArrayList<Timetable> expectedTimetable = new ArrayList<>(3);

            while (expectedTimetable.size() < 3)
                expectedTimetable.add(new Timetable());

            expectedTimetable.get(0).setDescription("Mo/Tu - Th/Fr");
            expectedTimetable.get(0).addMinutes(23, "31A");
            expectedTimetable.get(0).addMinutes(0, "31A");
            expectedTimetable.get(0).addMinutes(1, "31A");
            expectedTimetable.get(0).addMinutes(2, "31A");
            expectedTimetable.get(0).addMinutes(3, "31A");

            expectedTimetable.get(1).setDescription("Fr/Sa-Sa/Su");
            expectedTimetable.get(1).addMinutes(23, "31A");
            expectedTimetable.get(1).addMinutes(0, "31");
            expectedTimetable.get(1).addMinutes(1, "31");
            expectedTimetable.get(1).addMinutes(2, "31A");
            expectedTimetable.get(1).addMinutes(3, "31A");

            expectedTimetable.get(2).setDescription("Su/Mo");
            expectedTimetable.get(2).addMinutes(23, "31A");
            expectedTimetable.get(2).addMinutes(0, "31A");
            expectedTimetable.get(2).addMinutes(1, "31A");
            expectedTimetable.get(2).addMinutes(2, "31A");
            expectedTimetable.get(2).addMinutes(3, "31A");

            CracowLine line = new CracowLine(605);
            Direction direction = line.getDirectionsList().get(0);
            Stop stop = line.getStopsList(direction).get(0);
            List<Timetable> actualTimetable = line.getTimetables(direction, stop);

            assertThat(actualTimetable).isEqualTo(expectedTimetable);
        }

        @Test
        void timetableNightLine605Direction1Stop3() throws ConnectionError, ParsingException
        {
            ArrayList<Timetable> expectedTimetable = new ArrayList<>(3);

            while (expectedTimetable.size() < 3)
                expectedTimetable.add(new Timetable());

            expectedTimetable.get(0).setDescription("Mo/Tu - Th/Fr");
            expectedTimetable.get(0).addMinutes(23, "34A");
            expectedTimetable.get(0).addMinutes(0, "34A");
            expectedTimetable.get(0).addMinutes(1, "34A");
            expectedTimetable.get(0).addMinutes(2, "34A");
            expectedTimetable.get(0).addMinutes(3, "34A");

            expectedTimetable.get(1).setDescription("Fr/Sa-Sa/Su");
            expectedTimetable.get(1).addMinutes(23, "34A");
            expectedTimetable.get(1).addMinutes(0, "34");
            expectedTimetable.get(1).addMinutes(1, "34");
            expectedTimetable.get(1).addMinutes(2, "34A");
            expectedTimetable.get(1).addMinutes(3, "34A");

            expectedTimetable.get(2).setDescription("Su/Mo");
            expectedTimetable.get(2).addMinutes(23, "34A");
            expectedTimetable.get(2).addMinutes(0, "34A");
            expectedTimetable.get(2).addMinutes(1, "34A");
            expectedTimetable.get(2).addMinutes(2, "34A");
            expectedTimetable.get(2).addMinutes(3, "34A");

            CracowLine line = new CracowLine(605);
            Direction direction = line.getDirectionsList().get(0);
            Stop stop = line.getStopsList(direction).get(2);
            List<Timetable> actualTimetable = line.getTimetables(direction, stop);

            assertThat(actualTimetable).isEqualTo(expectedTimetable);
        }

        @Test
        void lineWith1Timetable() throws ConnectionError, ParsingException
        {
            ArrayList<Timetable> expectedTimetable = new ArrayList<>(1);

            expectedTimetable.add(new Timetable());

            expectedTimetable.get(0).setDescription("All days of the week");
            expectedTimetable.get(0).addMinutes(23, "22");
            expectedTimetable.get(0).addMinutes(0, "22");
            expectedTimetable.get(0).addMinutes(1, "22");
            expectedTimetable.get(0).addMinutes(2, "22");
            expectedTimetable.get(0).addMinutes(3, Arrays.asList("22", "52"));

            CracowLine line = new CracowLine(608);
            Direction direction = line.getDirectionsList().get(1);
            Stop stop = line.getStopsList(direction).get(3);
            List<Timetable> actualTimetable = line.getTimetables(direction, stop);

            assertThat(actualTimetable).isEqualTo(expectedTimetable);
        }

        @Test
        void lineWith2Timetables() throws ConnectionError, ParsingException
        {
            ArrayList<Timetable> expectedTimetable = new ArrayList<>(2);

            while (expectedTimetable.size() < 2)
                expectedTimetable.add(new Timetable());

            expectedTimetable.get(0).setDescription("Weekday");
            expectedTimetable.get(0).addMinutes(6, "13");
            expectedTimetable.get(0).addMinutes(8, "12");
            expectedTimetable.get(0).addMinutes(10, "18");
            expectedTimetable.get(0).addMinutes(12, "21");
            expectedTimetable.get(0).addMinutes(15, "25");
            expectedTimetable.get(0).addMinutes(17, "01");

            expectedTimetable.get(1).setDescription("Saturdays");
            expectedTimetable.get(1).addMinutes(6, "16");
            expectedTimetable.get(1).addMinutes(8, "07");
            expectedTimetable.get(1).addMinutes(10, "20");
            expectedTimetable.get(1).addMinutes(12, "23");
            expectedTimetable.get(1).addMinutes(15, "04");
            expectedTimetable.get(1).addMinutes(16, "58");

            CracowLine line = new CracowLine(285);
            Direction direction = line.getDirectionsList().get(0);
            Stop stop = line.getStopsList(direction).get(6);
            List<Timetable> actualTimetable = line.getTimetables(direction, stop);

            assertThat(actualTimetable).isEqualTo(expectedTimetable);
        }
    }
}
