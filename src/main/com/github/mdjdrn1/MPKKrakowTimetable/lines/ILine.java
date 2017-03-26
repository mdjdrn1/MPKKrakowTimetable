package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.List;

public interface ILine
{
    int getLineNumber();
    List<Integer> getLineNumbersList()throws ConnectionError, ParsingException;
    List<Direction> getDirectionsList() throws ConnectionError, ParsingException;
    List<Stop> getStopsList(Direction direction) throws ConnectionError, ParsingException;
    List<Timetable> getTimetables(Direction direction, Stop stop) throws ConnectionError, ParsingException;
    List<Integer> getDelayList(Direction direction) throws ConnectionError, ParsingException;
}
