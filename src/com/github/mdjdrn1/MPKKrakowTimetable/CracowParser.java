package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.mdjdrn1.MPKKrakowTimetable.stuctures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.stuctures.Stop;

import java.io.IOException;
import java.util.ArrayList;
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

        if (items == null || items.isEmpty())
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
        List<Direction> directions = new ArrayList<Direction>();

        HtmlPage page = getHtmlPage(getLineUrl(lineNumber));

        HtmlParagraph paragraph = (HtmlParagraph) page.getFirstByXPath("//p[@style=' font-size: 40px;']");
        if (paragraph == null)
            throw new Exception("getDirectionsList() exception. Cannot found paragraph.");

        List<HtmlTableCell> items = (List<HtmlTableCell>) paragraph.getByXPath("../../../td[@style='text-align: left; white-space: nowrap; ']");
        if (items == null)
            throw new Exception("getDirectionsList() exception. Cannot find table cell.");
        else
        {
            List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) items.get(0).getByXPath("./a");


            if (itemAnchor.size() == 0 || itemAnchor.size() > 2)
            {
                throw new Exception("getDirectionsList() exception. Directions parsing error.");
            }

            String directionName = itemAnchor.get(0).asText();
            directionName = directionName.substring(directionName.lastIndexOf('-') + 1).trim();

            directions.add(new Direction(directionName, 1));

            if (itemAnchor.size() == 2)
            {
                directionName = itemAnchor.get(1).asText();
                directionName = directionName.substring(directionName.lastIndexOf('-') + 1).trim();

                directions.add(new Direction(directionName, 2));
            }
        }

        return directions;
    }

    public String getLineUrl(int lineNumber)
    {
        return linePageBase + lineNumber;
    }

    // TODO: add valid implementation
    @Override
    public List<Stop> getStopsList(int lineNumber, Direction direction) throws Exception
    {
        return new ArrayList<Stop>();
    }
}
