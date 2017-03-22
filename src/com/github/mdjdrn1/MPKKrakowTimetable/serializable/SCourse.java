package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

import java.util.List;

public class SCourse
{
    private String direction;
    private List<STimetable> timetable;
    private List<SStop> stop;

    public List<SStop> getStop()
    {
        return stop;
    }

    public void setStop(List<SStop> stop)
    {
        this.stop = stop;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public List<STimetable> getTimetable()
    {
        return timetable;
    }

    public void setTimetable(List<STimetable> timetable)
    {
        this.timetable = timetable;
    }

    @Override
    public String toString()
    {
        return "Line [stop = " + stop + ", direction = " + direction + ", timetable = " + timetable + "]";
    }
}