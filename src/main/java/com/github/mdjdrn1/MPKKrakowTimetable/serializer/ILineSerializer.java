package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.ConnectionError;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.ParsingException;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.SLine;

public interface ILineSerializer
{
    String serializeLine(SLine line) throws ParsingException, ConnectionError;
}
