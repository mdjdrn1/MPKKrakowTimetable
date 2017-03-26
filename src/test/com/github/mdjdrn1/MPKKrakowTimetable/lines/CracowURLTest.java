package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CracowURLTest
{
    @Test
    void shouldReturnValidLine52URL()
    {
        CracowURLCreator cracowURLCreator = new CracowURLCreator();
        assertThat(cracowURLCreator.getLineUrl(52)).matches(".*lang=(PL|EN|DE)&rozklad=\\d{8}&linia=52$");
    }

    @Test
    void shouldReturnValidLine100Stop3URL() throws ParsingException, ConnectionError
    {
        int lineNumber = 100;
        CracowLine line = new CracowLine(lineNumber);
        int directionID = 1;
        Direction direction = line.getDirectionsList().get(directionID - 1);
        int stopID = 3;
        Stop stop = line.getStopsList(direction).get(stopID - 1);

        CracowURLCreator cracowURLCreator = new CracowURLCreator();
        String actualURL = cracowURLCreator.getLineUrl(lineNumber, direction, stop);

        assertThat(actualURL)
                .matches(".*lang=(PL|EN|DE)&rozklad=\\d{8}&linia=" + lineNumber + "__" + directionID + "__" + stopID);
    }
}
