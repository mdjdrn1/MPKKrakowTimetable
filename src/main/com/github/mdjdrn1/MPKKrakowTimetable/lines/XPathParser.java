package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

import java.io.IOException;

class XPathParser
{
    protected static HtmlPage getHtmlPage(String url) throws Exception
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

    protected static Integer getTableCellValue(HtmlTableCell item)
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
}
