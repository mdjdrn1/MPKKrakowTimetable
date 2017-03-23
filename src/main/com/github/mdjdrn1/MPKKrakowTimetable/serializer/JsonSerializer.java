package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.ILine;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.SerializableLine;
import com.github.mdjdrn1.MPKKrakowTimetable.lines.SerializableLineBuilder;
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
