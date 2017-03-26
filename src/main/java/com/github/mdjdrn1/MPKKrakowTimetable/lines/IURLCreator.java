package com.github.mdjdrn1.MPKKrakowTimetable.lines;

public interface IURLCreator
{
    String getLineUrl(int lineNumber);
    String getLineUrl(int lineNumber, Direction direction);
    String getLineUrl(int lineNumber, Direction direction, Stop stop);
}
