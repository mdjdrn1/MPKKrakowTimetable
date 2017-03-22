package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

import java.util.List;

public class STimetable
{
    private String description;

    private List<List<String>> hours;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getHour(int hour)
    {
        return hours.get(hour);
    }

    public void setHours(List<List<String>> hours)
    {
        this.hours = hours;
    }

    @Override
    public String toString()
    {
        return "Line [description = " + description + ", hours = " + hours + "]";
    }
}