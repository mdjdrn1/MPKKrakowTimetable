package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Timetable;

import java.io.IOException;
import java.util.*;

public class CracowLine implements ILine
{
    private static List<Integer> lineNumbersList;

    static
    {
        try
        {
            lineNumbersList = getLineNumbersList();
        }
        catch (Exception e)
        {
            System.out.println("Failed initializing static CracowLine members.");
        }
    }

    private static CracowURLCreator urlCreator = new CracowURLCreator();
    private final Integer lineNumber;

    public static List<Integer> getLineNumbersList() throws Exception
    {
        List<Integer> lineNumbers = new ArrayList<>();

        HtmlPage page = getHtmlPage(urlCreator.getHomePage());

        List<HtmlTableCell> items = (List<HtmlTableCell>) page.getByXPath("//td[@class='linia_table_left']");

        if (items == null)
            throw new Exception("getLineNumbersList() exception. Cannot parse line numbers.");
        else
        {
            for (HtmlElement htmlItem : items)
            {
                List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) htmlItem.getByXPath("./a");
                for (HtmlAnchor item : itemAnchor)
                {
                    if (item == null)
                        throw new Exception("getLineNumbersList() exception. Cannot parse line numbers");
                    else
                        lineNumbers.add(Integer.valueOf(item.asText()));
                }
            }
        }

        return lineNumbers;
    }

    public CracowLine(int lineNumber) throws Exception
    {
        if (!lineNumbersList.contains(lineNumber))
            throw new Exception("Line " + lineNumber + " doesn't exist.");

        this.lineNumber = lineNumber;
    }

    public boolean isNightLine()
    {
        boolean isTramNightLine = lineNumber >= 60 || lineNumber < 70;
        boolean isBusUrbanNightLine = lineNumber >= 600 || lineNumber < 700;
        boolean isBusAgglomerationNightLine = lineNumber >= 900 || lineNumber < 1000;

        return isTramNightLine || isBusUrbanNightLine || isBusAgglomerationNightLine;
    }

    @Override
    public List<Direction> getDirectionsList() throws Exception
    {
        List<Direction> directions = new ArrayList<>();

        HtmlPage page = getHtmlPage(urlCreator.getLineUrl(lineNumber));

        HtmlParagraph paragraph = (HtmlParagraph) page.getFirstByXPath("//p[contains(@style,'font-size: 40px;')]");
        if (paragraph == null)
            throw new Exception("getDirectionsList() exception. Cannot parse directions.");

        List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) paragraph.getByXPath("../../../td[@style='text-align: left; white-space: nowrap; ']/a");

        if (itemAnchor.size() == 0)
        {
            throw new Exception("getDirectionsList() exception. Cannot parse directions.");
        }

        int id = 1;

        for (HtmlAnchor anchor : itemAnchor)
        {
            String directionName = anchor.asText();
            if (id % 2 == 1)
                directionName = directionName.substring(directionName.lastIndexOf('-') + 1).trim();
            else
                directionName = directionName.substring(directionName.lastIndexOf('-') + 1).trim();

            directions.add(new Direction(directionName, id++));
        }

        return directions;
    }

    @Override
    public List<Stop> getStopsList(Direction direction) throws Exception
    {
        List<Stop> stopsList = new ArrayList<>();

        HtmlPage page = getHtmlPage(urlCreator.getLineUrl(lineNumber, direction));

        List<HtmlAnchor> htmlAnchors = (List<HtmlAnchor>) page.getByXPath("//td[@style=' text-align: right; ']/a");

        if (htmlAnchors == null)
            throw new Exception("getStopsList() exception. Cannot parse stops.");

        for (HtmlAnchor item : htmlAnchors)
        {
            if (item != null)
            {
                String stopName = item.asText();
                Integer stopID = Integer.valueOf(item.getHrefAttribute().split("\\d__\\d")[1].replaceFirst("__", ""));

                stopsList.add(new Stop(stopName, stopID));
            }
        }

        return stopsList;
    }

    /**
     * @return ArrayList (size up to 3 - index 0: weekday; index 1: saturdays; index 2: holidays)
     * of ArrayLists - representing hour (size 24)
     * of List<String> (representing departure minutes)
     */
    @Override
    public ArrayList<Timetable> getTimetables(Direction direction, Stop stop) throws Exception
    {
        // TODO: doesn't work for timetable with only weekday or only weekday and saturday
        // TODO: take into account, some lines (e.g night lines) have timetable for all days of week or only friday/saturday etc.

        // TODO: detect size of timetableList
        int amountOfTimetables = 3;
        ArrayList<Timetable> timetableList = new ArrayList<>(amountOfTimetables);

        while (timetableList.size() < amountOfTimetables)
        {
            timetableList.add(new Timetable());
        }


        HtmlPage page = getHtmlPage(urlCreator.getLineUrl(lineNumber, direction));

        List<HtmlTableRow> items = (List<HtmlTableRow>) page.getByXPath("//tr[@style=' margin-bottom: 10px; ']");

        if (items == null || items.isEmpty())
        {
            throw new Exception("getStopsList() exception. Cannot parse stops.");
        }

        for (HtmlElement htmlItem : items)
        {
            List<HtmlTableCell> tableCells = (List<HtmlTableCell>) htmlItem.getByXPath("../tr/td");

            for (int i = 5; i + 3 < tableCells.size(); i += 4)
            {
                HtmlTableCell item = tableCells.get(i);
                Integer hour = getTableCellValue(item);

                if (hour == null)
                    break;

                for (int k = 0; k < 3; ++k)
                {
                    ArrayList<String> minutes = getAllTableCellValues(tableCells.get(i + k + 1));
                    if (minutes != null && !minutes.isEmpty())
                        timetableList.get(k).setMinutes(hour, minutes);
                }
            }
        }

        return timetableList;
    }

    private static Integer getTableCellValue(HtmlTableCell item)
    {
        try
        {
            return Integer.valueOf(item.asText().trim());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static ArrayList<String> getAllTableCellValues(HtmlTableCell item)
    {
        String itemAsText = item.asText().trim();
        if (itemAsText.isEmpty())
            return null;

        String[] stringValues = item.asText().split(" ");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(stringValues));

        return !values.isEmpty() ? values : null;
    }

    public static HtmlPage getHtmlPage(String url) throws Exception
    {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page = null;
        try
        {
            page = client.getPage(url);
        }
        catch (IOException e)
        {
            throw new Exception("Cannot parse page from url: \"" + url + "\".");
        }

        return page;
    }
}
