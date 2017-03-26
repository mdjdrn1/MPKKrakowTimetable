package com.github.mdjdrn1.MPKKrakowTimetable.lines;

public class SLineDirector
{
    public static SLine makeLine(ISLineBuilder lineBuilder) throws ParsingException, ConnectionError
    {
        SLine sLine = new SLine();
        sLine.setNumber(lineBuilder.buildLineNumber());
        sLine.setCourse(lineBuilder.buildCourses());

        return sLine;
    }
}
