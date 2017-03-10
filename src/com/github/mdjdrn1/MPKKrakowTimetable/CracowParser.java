package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CracowParser implements IParser
{
    private final String homePage;
    private final String linePageBase;

    public CracowParser() throws Exception
    {
        homePage = "http://rozklady.mpk.krakow.pl/";
        linePageBase = getLineUrlBase();
    }

    private String getLineUrlBase() throws Exception
    {
        String timetableURL = null;
        HtmlPage page = getHtmlPage(homePage);

        HtmlTableCell cell = (HtmlTableCell) page.getFirstByXPath("//td[@class='linia_table_left']");

        if (cell == null)
            throw new Exception("getLineUrlBase() exception. Cannot find td class='linia_table_left'.");

        HtmlAnchor itemAnchor = (HtmlAnchor) cell.getFirstByXPath("./a");
        if (itemAnchor == null)
            throw new Exception("getLineUrlBase() exception. Page content was invalid.");
        else
            timetableURL = itemAnchor.getHrefAttribute();

        // TODO: check if url has valid format
        timetableURL = timetableURL.replaceFirst("\\d$", ""); // removes last character

        return timetableURL;
    }

    public String getLineUrl(int lineNumber)
    {
        // TODO: check if line exists
        return linePageBase + lineNumber;
    }

    public String getLineUrl(int lineNumber, Direction direction)
    {
        return getLineUrl(lineNumber) + "__" + direction.getId();
    }

    public String getLineUrl(int lineNumber, Direction direction, Stop stop)
    {
        return getLineUrl(lineNumber, direction) + "__" + stop.getId();
    }

    private static HtmlPage getHtmlPage(String url) throws Exception
    {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(true);
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

    @Override
    public List<Integer> getLinesNumbersList() throws Exception
    {
        List<Integer> lineNumbers = new ArrayList<>();

        HtmlPage page = getHtmlPage(homePage);

        List<HtmlTableCell> items = (List<HtmlTableCell>) page.getByXPath("//td[@class='linia_table_left']");

        if (items == null)
            throw new Exception("getLinesNumbersList() exception. Cannot find td class='linia_table_left'.");
        else
        {
            for (HtmlElement htmlItem : items)
            {
                List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) htmlItem.getByXPath("./a");
                for (HtmlAnchor item : itemAnchor)
                {
                    if (item == null)
                        throw new Exception("getLinesNumbersList() exception. Lines numbers parsing error.");
                    else
                        lineNumbers.add(Integer.valueOf(item.asText()));
                }
            }
        }

        return lineNumbers;
    }

    @Override
    public List<Direction> getDirectionsList(int lineNumber) throws Exception
    {
        List<Direction> directions = new ArrayList<>();

        HtmlPage page = getHtmlPage(getLineUrl(lineNumber));

        HtmlParagraph paragraph = (HtmlParagraph) page.getFirstByXPath("//p[contains(@style,'font-size: 40px;')]");
        if (paragraph == null)
            throw new Exception("getDirectionsList() exception. Cannot found paragraph.");

        List<HtmlTableCell> items = (List<HtmlTableCell>) paragraph.getByXPath("../../../td[@style='text-align: left; white-space: nowrap; ']");
        if (items == null)
            throw new Exception("getDirectionsList() exception. Cannot find table cell.");
        else
        {
            List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) items.get(0).getByXPath("./a");


            if (itemAnchor.size() == 0)
            {
                throw new Exception("getDirectionsList() exception. Directions parsing error.");
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
        }

        return directions;
    }

    @Override
    public List<Stop> getStopsList(int lineNumber, Direction direction) throws Exception
    {
        List<Stop> stopsList = new ArrayList<>();

        HtmlPage page = getHtmlPage(getLineUrl(lineNumber, direction));

        List<HtmlTableCell> items = (List<HtmlTableCell>) page.getByXPath("//td[@style=' text-align: right; ']");

        if (items == null)
            throw new Exception("getStopsList() exception. Cannot find table cell.");
        else
        {

            for (HtmlElement htmlItem : items)
            {
                List<HtmlAnchor> htmlAnchors = (List<HtmlAnchor>) htmlItem.getByXPath("./a");
                if (htmlAnchors == null)
                    throw new Exception("getStopsList() exception. Cannot find anchors.");
                for (HtmlAnchor item : htmlAnchors)
                {
                    if (item != null)
                    {
                        String stopName = item.asText();
                        Integer stopID = Integer.valueOf(item.getHrefAttribute().split("\\d__\\d")[1].replaceFirst("__", ""));

                        stopsList.add(new Stop(stopName, stopID));
                    }
                }
            }
        }

        return stopsList;
    }

    /**
     * @return ArrayList (size up to 3 - index 0: weekday; index 1: saturdays; index 2: holidays)
     * of ArrayLists (size 24 for each - each representing hour)
     * of List<String> (representing departure minutes)
     */
    @Override
    public ArrayList<ArrayList<List<String>>> getTimetable(int lineNumber, Direction direction, Stop stop) throws Exception
    {
        // TODO: doesn't work for timetable with only weekday or only weekday and saturday
        // TODO: take into account, some lines (e.g night lines) have timetable for all days of week or only friday/saturday etc.

        ArrayList<ArrayList<List<String>>> timetable = new ArrayList<ArrayList<List<String>>>(3);

        while (timetable.size() < 3)
            timetable.add(new ArrayList<List<String>>(24));

        for (int i = 0; i < timetable.size(); ++i)
            while (timetable.get(i).size() < 24)
                timetable.get(i).add(new ArrayList<String>());

        HtmlPage page = getHtmlPage(getLineUrl(lineNumber, direction));

        List<HtmlTableRow> items = (List<HtmlTableRow>) page.getByXPath("//tr[@style=' margin-bottom: 10px; ']");

        if (items == null || items.isEmpty())
        {
            throw new Exception("getTimetable() exception. Cannot find tr style=margin-bottom: 10px;");
        }
        else
        {
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
                            timetable.get(k).set(hour, minutes);
                    }
                }
            }
        }
        return timetable;
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
}
