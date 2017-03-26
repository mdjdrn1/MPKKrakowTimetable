package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer implements ILineSerializer
{
    @Override
    public String serializeLine(SLine line) throws ParsingException, ConnectionError
    {
        return serializeLine(line, false);
    }

    public String serializeLine(SLine line, boolean prettyPrint) throws ParsingException, ConnectionError
    {
        Gson gson = prettyPrint ? new GsonBuilder().setPrettyPrinting().create() : new GsonBuilder().create();
        return gson.toJson(line);
    }
}
