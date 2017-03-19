package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.html.*;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CracowLine extends XPathParser implements ILine
{
    private final int lineNumber;


    private static CracowURLCreator urlCreator = new CracowURLCreator();

    public List<Integer> getLineNumbersList() throws Exception
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
        if (!getLineNumbersList().contains(lineNumber))
            throw new Exception("Line " + lineNumber + " doesn't exist.");

        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber()
    {
        return lineNumber;
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
        HtmlPage page = getHtmlPage(urlCreator.getLineUrl(lineNumber, direction, stop));

        int amountOfTimetables = amountOfTimetablesOnPage(page);
        ArrayList<Timetable> timetableList = new ArrayList<>(amountOfTimetables);

        while (timetableList.size() < amountOfTimetables)
        {
            timetableList.add(new Timetable());
        }

        HtmlTableRow row = page.getFirstByXPath("//tr[@style=' margin-bottom: 10px; ']");
        List<HtmlTableCell> cells = (List<HtmlTableCell>) row.getByXPath("../tr/td");

        if (cells.isEmpty())
        {
            throw new Exception("getStopsList() exception. Cannot parse timetable.");
        }

        int indexOfFirstHour = findIndexOfFirstHour(cells);

        for (int i = indexOfFirstHour; i + amountOfTimetables < cells.size(); i += amountOfTimetables + 1)
        {
            HtmlTableCell item = cells.get(i);
            Integer hour = getTableCellValue(item);
            if (hour == null)
                break;

            for (int k = 0; k < amountOfTimetables; ++k)
            {
                ArrayList<String> minutes = getAllTableCellValues(cells.get(i + k + 1));
                if (minutes != null && !minutes.isEmpty())
                    timetableList.get(k).setMinutes(hour, minutes);
            }
        }

        ArrayList<String> descriptions = getTimetablesDescriptions(indexOfFirstHour, amountOfTimetables, cells);
        if(descriptions.size() != amountOfTimetables)
            throw new Exception("getStopsList() exception. Cannot parse descriptions for timetables.");

        for(int i = 0; i < amountOfTimetables; ++i)
        {
            timetableList.get(i).setDescription(descriptions.get(i));
        }

        return timetableList;
    }

    private int amountOfTimetablesOnPage(HtmlPage page)
    {
        List<HtmlTableCell> cells = (List<HtmlTableCell>) page.getByXPath("//tr[@style=' margin-bottom: 10px; ']/td");
        int amountOfTimetables = cells.size() - 2;

        return amountOfTimetables;
    }

    private int findIndexOfFirstHour(List<HtmlTableCell> tableCells)
    {
        int indexOfFirstHour = 0;
        while (getTableCellValue(tableCells.get(indexOfFirstHour)) == null)
            ++indexOfFirstHour;

        return indexOfFirstHour;
    }

    private ArrayList<String> getAllTableCellValues(HtmlTableCell item)
    {
        String itemAsText = item.asText().trim();
        if (itemAsText.isEmpty())
            return null;

        String[] stringValues = item.asText().split(" ");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(stringValues));

        return !values.isEmpty() ? values : null;
    }

    private ArrayList<String> getTimetablesDescriptions(int indexOfFirstHour, int amountOfTimetables, List<HtmlTableCell> cells)
    {
        ArrayList<String> descriptions = new ArrayList<>();

        for(int i = 0; i < indexOfFirstHour && amountOfTimetables > 0; ++i)
        {
            String cellText = cells.get(i).asText();
            if(!cellText.isEmpty() && !cellText.equals("Hour"))
            {
                descriptions.add(cellText);
                --amountOfTimetables;
            }
        }

        return descriptions;
    }
}
