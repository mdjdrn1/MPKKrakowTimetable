package com.github.mdjdrn1.MPKKrakowTimetable.structures;

import java.util.ArrayList;
import java.util.List;

public class Timetable
{
    private String description;
    private ArrayList<List<String>> timetableList;

    private static final int HOURS = 24;

    public Timetable()
    {
        this("");
    }

    public Timetable(String description)
    {
        this.description = description;

        this.timetableList = createTimetableList();
    }

    private ArrayList<List<String>> createTimetableList()
    {
        ArrayList<List<String>> timetableList = new ArrayList<>(HOURS);

        while (timetableList.size() < HOURS)
        {
            timetableList.add(new ArrayList<>());
        }

        return timetableList;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getMinutes(int hour)
    {
        return timetableList.get(hour);
    }

    public void setMinutes(int hour, List<String> minutes)
    {
        timetableList.set(hour, minutes);
    }

    public void addMinutes(int hour, String minute)
    {
        timetableList.get(hour).add(minute);
    }

    public void addMinutes(int hour, List<String> minutes)
    {
        timetableList.get(hour).addAll(minutes);
    }

    public ArrayList<List<String>> getTimetable()
    {
        return timetableList;
    }

    public int size()
    {
        return timetableList.size();
    }

    @Override
    public String toString()
    {
        return "Timetable{" +
                "description='" + description + '\'' +
                ", timetableList=" + timetableList +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Timetable)) return false;

        Timetable timetable = (Timetable) o;

        return (description != null ? description.equals(timetable.description) : timetable.description == null) && (timetableList != null ? timetableList.equals(timetable.timetableList) : timetable.timetableList == null);
    }

    @Override
    public int hashCode()
    {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (timetableList != null ? timetableList.hashCode() : 0);
        return result;
    }
}
