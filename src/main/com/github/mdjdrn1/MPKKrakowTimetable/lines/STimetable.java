package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.List;

class STimetable
{
    private String description;

    private List<List<String>> hours;

    public String getDescription()
    {
        return description;
    }

    void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getHour(int hour)
    {
        return hours.get(hour);
    }

    void setHours(List<List<String>> hours)
    {
        this.hours = hours;
    }

    @Override
    public String toString()
    {
        return "Line [description = " + description + ", hours = " + hours + "]";
    }
}