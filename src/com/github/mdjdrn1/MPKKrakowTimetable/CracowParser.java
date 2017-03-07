package com.github.mdjdrn1.MPKKrakowTimetable;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CracowParser implements IParser
{
    static private final String homepage = "http://rozklady.mpk.krakow.pl/";

    public List<Integer> getLinesNumbersList() throws Exception
    {
        List<Integer> lineNumbers = new ArrayList<>();

        HtmlPage page = getHtmlPage(homepage);

        List<HtmlTableCell> items = (List<HtmlTableCell>) page.getByXPath("//td[@class='linia_table_left']");

        if (items == null)
            System.out.println("items null");

        if (items.isEmpty())
        {
            System.out.println("No items found !");
        }
        else
        {
            for (HtmlElement htmlItem : items)
            {
                List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) htmlItem.getByXPath("./a");
                for (HtmlAnchor item : itemAnchor)
                {
                    if (item == null)
                        System.out.println("anchor null");
                    else
                    {
                        lineNumbers.add(Integer.valueOf(item.asText()));
                    }
                }
            }
        }

        return lineNumbers;
    }

    private HtmlPage getHtmlPage(String url) throws Exception
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
            throw new Exception("Cannot parse page from url: " + url + ".");
        }

        return page;
    }
}
