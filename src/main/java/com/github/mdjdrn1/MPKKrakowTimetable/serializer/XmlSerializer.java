package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.*;
import com.thoughtworks.xstream.XStream;

public class XmlSerializer implements ILineSerializer
{
    private final static XStream xstream = buildNewXStream();

    private static XStream buildNewXStream()
    {
        XStream xstream = new XStream();

        xstream.alias("line", SLine.class);
        xstream.alias("course", SCourse.class);
        xstream.alias("timetable", STimetable.class);
        xstream.alias("stop", SStop.class);

        return xstream;
    }

    @Override
    public String serializeLine(SLine line) throws ParsingException, ConnectionError
    {
        return xstream.toXML(line);
    }
}
