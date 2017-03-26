package com.github.mdjdrn1.MPKKrakowTimetable.serializer;

import com.github.mdjdrn1.MPKKrakowTimetable.lines.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AllLinesSerializer
{

    static
    {
        Configurator.defaultConfig()
                .writer(new FileWriter("log\\AllLinesSerializerLog.txt"))
                .activate();
    }

    public static void main(String[] args)
    {
        try
        {
            CracowLine randomLine = new CracowLine(1);
            serializeAllLines(randomLine);
        }
        catch (ParsingException | ConnectionError e)
        {
            Logger.error(e.getMessage());
        }

    }

    private static void serializeAllLines(CracowLine randomLine)
    {
        try
        {
            List<Integer> linesNumbers = randomLine.getLineNumbersList();
            JsonSerializer serializer = new JsonSerializer();

            linesNumbers.parallelStream().forEach((lineNumber) ->
            {
                Logger.info("Parsing line " + lineNumber + ".");
                try
                {
                    saveJsonStringToFile(lineNumber, serializer.serializeLine(
                            SLineDirector.makeLine(
                                    new SLineBuilder(
                                            new CracowLine(lineNumber)))
                    ));
                }
                catch (ParsingException | ConnectionError e)
                {
                    Logger.error(e.getMessage() + " Failed parsing line " + lineNumber + ".");
                }
            });
        }
        catch (ParsingException | ConnectionError e)
        {
            Logger.error(e.getMessage());
        }
    }

    private static void saveJsonStringToFile(int lineNumber, String jsonString)
    {
        String dirsPath = "output/json/";
        File dirs = new File(dirsPath);
        if (!dirs.exists())
            dirs.mkdirs();

        String path = dirsPath + lineNumber + ".json";
        File file = new File(path);

        if (!file.exists())
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
