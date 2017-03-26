package com.github.mdjdrn1.MPKKrakowTimetable.lines;

class Stop
{
    private final String name;
    private final Integer id;
    private final boolean onDemand;

    public Stop(String name, Integer id)
    {
        this(name, id, false);
    }

    public Stop(String name, Integer id, boolean onDemand)
    {
        this.name = name;
        this.id = id;
        this.onDemand = onDemand;
    }

    public String getName()
    {
        return name;
    }

    public Integer getId()
    {
        return id;
    }

    public boolean isOnDemand()
    {
        return onDemand;
    }

    @Override
    public String toString()
    {
        String string = "Stop{" +
                "name='" + name + '\'' +
                ", id=" + id;

        if (onDemand)
            string += ", onDemand";

        string += '}';

        return string;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;

        Stop stop = (Stop) o;

        if (onDemand != stop.onDemand) return false;
        if (name != null ? !name.equals(stop.name) : stop.name != null) return false;
        return id != null ? id.equals(stop.id) : stop.id == null;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (onDemand ? 1 : 0);
        return result;
    }
}
