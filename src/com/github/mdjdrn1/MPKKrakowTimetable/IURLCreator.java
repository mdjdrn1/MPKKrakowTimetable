package com.github.mdjdrn1.MPKKrakowTimetable;

import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;

public interface IURLCreator
{
    String getLineUrl(int lineNumber);
    String getLineUrl(int lineNumber, Direction direction);
    String getLineUrl(int lineNumber, Direction direction, Stop stop);
}
