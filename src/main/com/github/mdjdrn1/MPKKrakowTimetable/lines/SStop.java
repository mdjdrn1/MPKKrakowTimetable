package com.github.mdjdrn1.MPKKrakowTimetable.lines;

class SStop
{
    private int id;
    private String name;
    private boolean onDemand;
    private int delay;

    public int getId()
    {
        return id;
    }

    void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    void setName(String name)
    {
        this.name = name;
    }

    public int getDelay()
    {
        return delay;
    }

    void setDelay(int delay)
    {
        this.delay = delay;
    }

    public boolean isOnDemand()
    {
        return onDemand;
    }

    void setOnDemand(boolean onDemand)
    {
        this.onDemand = onDemand;
    }

    @Override
    public String toString()
    {
        return "Line [id = " + id + ", name = " + name + ", onDemand = " + onDemand +", delay = " + delay + "]";
    }
}