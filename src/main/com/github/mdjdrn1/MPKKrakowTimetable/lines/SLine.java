package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.List;

class SLine
{
    private int number;
    private List<SCourse> course;

    public List<SCourse> getCourse()
    {
        return course;
    }

    void setCourse(List<SCourse> course)
    {
        this.course = course;
    }

    public int getNumber()
    {
        return number;
    }

    void setNumber(int number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Line [course = " + course + ", number = " + number + "]";
    }
}