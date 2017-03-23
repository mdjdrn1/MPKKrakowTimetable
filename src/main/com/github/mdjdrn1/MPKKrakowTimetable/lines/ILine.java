package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.List;

public interface ILine
{
    int getLineNumber();
    List<Integer> getLineNumbersList() throws Exception;
    List<Direction> getDirectionsList() throws Exception;
    List<Stop> getStopsList(Direction direction) throws Exception;
    List<Timetable> getTimetables(Direction direction, Stop stop) throws Exception;
    List<Integer> getDelayList(Direction direction) throws Exception;
}
