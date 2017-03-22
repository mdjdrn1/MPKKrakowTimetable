package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

public class SStop
{
    private int id;
    private String name;
    private boolean onDemand;
    private int delay;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public boolean isOnDemand()
    {
        return onDemand;
    }

    public void setOnDemand(boolean onDemand)
    {
        this.onDemand = onDemand;
    }

    @Override
    public String toString()
    {
        return "Line [id = " + id + ", name = " + name + ", delay = " + delay + "]";
    }
}