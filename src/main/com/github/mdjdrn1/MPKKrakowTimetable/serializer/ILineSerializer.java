package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.ConnectionError;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.ILine;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.ParsingException;

public interface ILineSerializer
{
    public String serializeLine(ILine sLine) throws ParsingException, ConnectionError;
}
