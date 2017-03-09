package com.github.mdjdrn1.MPKKrakowTimetable;

import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;

import java.util.ArrayList;
import java.util.List;

public interface IParser
{
    List<Integer> getLinesNumbersList() throws Exception;

    List<Direction> getDirectionsList(int lineNumber) throws Exception;

    List<Stop> getStopsList(int lineNumber, Direction direction) throws Exception;

    ArrayList<ArrayList<List<String>>> getTimetable(int lineNumber, Direction direction, Stop stop) throws Exception;
}
