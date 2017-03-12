package com.github.mdjdrn1.MPKKrakowTimetable.structures;

import java.util.ArrayList;
import java.util.Comparator;
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

        ArrayList<List<String>> timetableList1 = this.timetableList;
        ArrayList<List<String>> timetableList2 = timetable.timetableList;

        if(timetableList1 == null)
            return timetableList2 == null;

        for(List<String> list : timetableList1)
        {
            list.sort(Comparator.comparingInt(min -> Integer.parseInt(min.substring(0, 2))));
        }

        for(List<String> list : timetableList2)
        {
            list.sort(Comparator.comparingInt(min -> Integer.parseInt(min.substring(0, 2))));
        }

        return timetableList1.equals(timetableList2);
    }

    @Override
    public int hashCode()
    {
        return timetableList != null ? timetableList.hashCode() : 0;
    }
}
