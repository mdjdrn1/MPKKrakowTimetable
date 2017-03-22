package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

public class SerializableLine
{
    private SLine line;

    public SLine getLine()
    {
        return line;
    }
    public void setLine(SLine line)
    {
        this.line = line;
    }

    @Override
    public String toString()
    {
        return "Line [line = " + line + "]";
    }
}