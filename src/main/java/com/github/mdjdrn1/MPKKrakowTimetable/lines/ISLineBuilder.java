package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.List;

public interface ISLineBuilder
{
    int buildLineNumber();
    List<SCourse> buildCourses() throws ParsingException, ConnectionError;
}
