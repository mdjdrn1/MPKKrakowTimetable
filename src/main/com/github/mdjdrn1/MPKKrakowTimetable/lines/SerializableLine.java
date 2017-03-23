package com.github.mdjdrn1.MPKKrakowTimetable.lines;

public class SerializableLine
{
    private SLine line;

    public SLine getLine()
    {
        return line;
    }
    void setLine(SLine line)
    {
        this.line = line;
    }

    @Override
    public String toString()
    {
        return "Line [line = " + line + "]";
    }
}