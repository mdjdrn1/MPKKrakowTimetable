package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

class XPathParser
{
    public static HtmlPage getHtmlPage(String url) throws ConnectionError
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
            throw new ConnectionError("Failed loading url: \"" + url + "\".");
        }

        return page;
    }
}
