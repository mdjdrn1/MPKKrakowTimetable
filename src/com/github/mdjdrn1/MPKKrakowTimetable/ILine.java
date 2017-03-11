package com.github.mdjdrn1.MPKKrakowTimetable;

import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;

import java.util.ArrayList;
import java.util.List;

public interface ILine
{
    List<Direction> getDirectionsList() throws Exception;
    List<Stop> getStopsList(Direction direction) throws Exception;
    ArrayList<ArrayList<List<String>>> getTimetable(Direction direction, Stop stop) throws Exception;
}
