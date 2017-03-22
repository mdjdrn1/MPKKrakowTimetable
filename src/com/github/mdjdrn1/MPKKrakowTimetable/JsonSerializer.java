package com.github.mdjdrn1.MPKKrakowTimetable;

import com.github.mdjdrn1.MPKKrakowTimetable.serializable.SerializableLine;
import com.github.mdjdrn1.MPKKrakowTimetable.serializable.SerializableLineBuilder;
import com.google.gson.Gson;

public class JsonSerializer implements ILineSerializer
{
    public String serializeLine(ILine line) throws Exception
    {
        SerializableLineBuilder builder = new SerializableLineBuilder(line);
        SerializableLine sLine = builder.getSerializableLine();

        Gson gson = new Gson();
        return gson.toJson(sLine);
    }
}
