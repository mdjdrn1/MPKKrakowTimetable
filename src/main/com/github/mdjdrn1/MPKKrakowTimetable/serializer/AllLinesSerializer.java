package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.CracowLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AllLinesSerializer
{
    public static void main(String[] args) throws Exception
    {
        CracowLine randomLine = new CracowLine(1);

        serializeAllLines(randomLine);
    }

    private static void serializeAllLines(CracowLine randomLine) throws Exception
    {
        List<Integer> linesNumbers = randomLine.getLineNumbersList();
        JsonSerializer serializer = new JsonSerializer();

        linesNumbers.parallelStream().forEach((lineNumber) ->
        {
            System.out.println("Parsing line " + lineNumber);
            try
            {
                saveJsonStringToFile(lineNumber, serializer.serializeLine(new CracowLine(lineNumber)));
            }
            catch (Exception e)
            {
                System.out.println("Failed parsing line " + lineNumber);
                System.out.println("getMessage() " + e.getMessage());
            }
        });
    }

    private static void saveJsonStringToFile(int lineNumber, String jsonString)
    {
        String dirsPath = "output/json/";
        File dirs = new File(dirsPath);
        if(!dirs.exists())
            dirs.mkdirs();

        String path = dirsPath + lineNumber + ".json";
        File file = new File(path);

        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException ignored)
            {
            }
        }

        PrintWriter writer;
        try
        {
            writer = new PrintWriter(path);
            writer.print(jsonString);
            writer.close();
        }
        catch (FileNotFoundException ignored)
        {
        }
    }
}
