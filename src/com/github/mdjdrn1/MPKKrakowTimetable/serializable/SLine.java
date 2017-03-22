package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

import java.util.List;

public class SLine
{
    private int number;
    private List<SCourse> course;

    public List<SCourse> getCourse()
    {
        return course;
    }

    public void setCourse(List<SCourse> course)
    {
        this.course = course;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Line [course = " + course + ", number = " + number + "]";
    }
}