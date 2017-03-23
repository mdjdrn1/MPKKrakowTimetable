package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.ILine;

public interface ILineSerializer
{
    public String serializeLine(ILine sLine) throws Exception;
}
