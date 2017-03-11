package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;

public class CracowURLCreator implements IURLCreator
{
    private static final String homePage = "http://rozklady.mpk.krakow.pl/";
    private static String linePageBase;

    static
    {
        try
        {
            linePageBase = getLineUrlBase();
        }
        catch (Exception e)
        {
            System.out.println("Failed initializing static CracowLine members.");
        }
    }

    private static String getLineUrlBase() throws Exception
    {
        String timetableURL;
        HtmlPage page = CracowLine.getHtmlPage(homePage);

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

    public static String getHomePage()
    {
        return homePage;
    }
}
