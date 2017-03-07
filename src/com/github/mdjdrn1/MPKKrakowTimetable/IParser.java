package com.github.mdjdrn1.MPKKrakowTimetable;

import com.github.mdjdrn1.MPKKrakowTimetable.stuctures.Direction;

import java.util.List;

public interface IParser
{
    List<Integer> getLinesNumbersList() throws Exception;

    List<Direction> getDirectionsList(int lineNumber) throws Exception;
}
