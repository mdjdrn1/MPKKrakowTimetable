package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import com.gargoylesoftware.htmlunit.html.*;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CracowLine implements ILine
{
    private final int lineNumber;
    private static CracowURLCreator urlCreator = new CracowURLCreator();
    
    public List<Integer> getLineNumbersList() throws ParsingException, ConnectionError
    {
        List<Integer> lineNumbers = new ArrayList<>();

        HtmlPage page = XPathParser.getHtmlPage(CracowURLCreator.getHomePage());

        List<HtmlTableCell> items = (List<HtmlTableCell>) page.getByXPath("//td[@class='linia_table_left']");

        if (items == null)
            throw new ParsingException("Cannot parse line numbers. Method: getLineNumbersList().");
        else
        {
            for (HtmlElement htmlItem : items)
            {
                List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) htmlItem.getByXPath("./a");
                for (HtmlAnchor item : itemAnchor)
                {
                    if (item == null)
                        throw new ParsingException("Cannot parse line numbers. Method: getLineNumbersList().");
                    else
                        lineNumbers.add(Integer.valueOf(item.asText()));
                }
            }
        }

        return lineNumbers;
    }

    public CracowLine(int lineNumber) throws InvalidParameterException, ParsingException, ConnectionError
    {
        if (!getLineNumbersList().contains(lineNumber))
            throw new InvalidParameterException("Line " + lineNumber + " doesn't exist.");

        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber()
    {
        return lineNumber;
    }

    public boolean isNightLine()
    {
        boolean isTramNightLine = lineNumber >= 60 && lineNumber < 70;
        boolean isBusUrbanNightLine = lineNumber >= 600 && lineNumber < 700;
        boolean isBusAgglomerationNightLine = lineNumber >= 900 && lineNumber < 1000;

        return isTramNightLine || isBusUrbanNightLine || isBusAgglomerationNightLine;
    }

    @Override
    public List<Direction> getDirectionsList() throws ConnectionError, ParsingException
    {
        List<Direction> directions = new ArrayList<>();

        HtmlPage page = XPathParser.getHtmlPage(urlCreator.getLineUrl(lineNumber));

        HtmlParagraph paragraph = page.getFirstByXPath("//p[contains(@style,'font-size: 40px;')]");
        if (paragraph == null)
            throw new ParsingException("Cannot parse directions. Method: getDirectionsList().");

        List<HtmlAnchor> itemAnchor = (List<HtmlAnchor>) paragraph.getByXPath("../../../td[@style='text-align: left; white-space: nowrap; ']/a");

        if (itemAnchor.size() == 0)
            throw new ParsingException("Cannot parse directions. Method: getDirectionsList().");

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
    public List<Stop> getStopsList(Direction direction) throws ConnectionError, ParsingException
    {
        List<Stop> stopsList = new ArrayList<>();

        HtmlPage page = XPathParser.getHtmlPage(urlCreator.getLineUrl(lineNumber, direction));

        List<HtmlAnchor> htmlAnchors = (List<HtmlAnchor>) page.getByXPath("//td[@style=' text-align: right; ']/a");

        if (htmlAnchors == null)
            throw new ParsingException("Cannot parse stops list. Method: getStopsList().");

        for (HtmlAnchor item : htmlAnchors)
        {
            if (item != null)
            {
                String stopName = item.asText();
                Integer stopID = Integer.valueOf(item.getHrefAttribute().split("\\d__\\d")[1].replaceFirst("__", ""));
                boolean onDemand = isStopOnDemand(item);

                stopsList.add(new Stop(stopName, stopID, onDemand));
            }
        }

        return stopsList;
    }

    private boolean isStopOnDemand(HtmlAnchor item)
    {
        List<HtmlTableCell> cells = ((List<HtmlTableCell>) item.getByXPath("../../td"));
        HtmlTableCell onDemandCell = cells.size() >= 3 ? cells.get(2) : null;
        boolean onDemand = false;

        if (onDemandCell != null && onDemandCell.asText().equals("NZ"))
        {
            onDemand = true;
        }

        return onDemand;
    }

    /**
     * @return ArrayList (size up to 3 - index 0: weekday; index 1: saturdays; index 2: holidays)
     * of ArrayLists - representing hour (size 24)
     * of List<String> (representing departure minutes)
     */
    @Override
    public List<Timetable> getTimetables(Direction direction, Stop stop) throws ConnectionError, ParsingException
    {
        HtmlPage page = XPathParser.getHtmlPage(urlCreator.getLineUrl(lineNumber, direction, stop));

        int amountOfTimetables = amountOfTimetablesOnPage(page);
        ArrayList<Timetable> timetableList = new ArrayList<>(amountOfTimetables);

        while (timetableList.size() < amountOfTimetables)
        {
            timetableList.add(new Timetable());
        }

        HtmlTableRow row = page.getFirstByXPath("//tr[@style=' margin-bottom: 10px; ']");
        List<HtmlTableCell> cells = (List<HtmlTableCell>) row.getByXPath("../tr/td");

        if (cells.isEmpty())
            throw new ParsingException("Cannot parse timetables. Method: getTimetables()");

        int indexOfFirstHour = findIndexOfFirstHour(cells);

        for (int i = indexOfFirstHour; i + amountOfTimetables < cells.size(); i += amountOfTimetables + 1)
        {
            HtmlTableCell item = cells.get(i);
            Integer hour = XPathParser.getTableCellValue(item);
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
        if (descriptions.size() != amountOfTimetables)
            throw new ParsingException("Cannot parse descriptions for timetables. Method: getTimetables()");

        for (int i = 0; i < amountOfTimetables; ++i)
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
        while (XPathParser.getTableCellValue(tableCells.get(indexOfFirstHour)) == null)
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

        for (int i = 0; i < indexOfFirstHour && amountOfTimetables > 0; ++i)
        {
            String cellText = cells.get(i).asText();
            if (!cellText.isEmpty() && !cellText.equals("Hour"))
            {
                descriptions.add(cellText);
                --amountOfTimetables;
            }
        }

        return descriptions;
    }

    public List<Integer> getDelayList(Direction direction) throws ConnectionError, ParsingException
    {
        List<Integer> delays = new ArrayList<>();

        List<Stop> stops = this.getStopsList(direction);
        Stop firstStop = stops.get(0);
        LocalTime firstStopFirstDeparture = getFirstDeparture(direction, firstStop);

        delays.add(0);  // first stop delay

        for (int i = 1; i < stops.size(); ++i)
        {
            Stop stop = stops.get(i);
            LocalTime stopFirstDeparture = getFirstDeparture(direction, stop);
            delays.add(getMinutesBetween(firstStopFirstDeparture, stopFirstDeparture));
        }

        return delays;
    }

    private Integer getMinutesBetween(LocalTime time1, LocalTime time2)
    {
        Integer minutesBetween = (int) ChronoUnit.MINUTES.between(time1, time2);

        return minutesBetween >= 0 ? minutesBetween : minutesBetween + 60 * 24;
    }

    private LocalTime getFirstDeparture(Direction direction, Stop stop) throws ConnectionError, ParsingException
    {
        Timetable timetable = this.getTimetables(direction, stop).get(0);

        LocalTime firstDeparture = null;
        int firstHourToCheck = !isNightLine() ? 3 : 22;
        int lastHourToCheck = !isNightLine() ? 2 : 5;

        for (int hour = firstHourToCheck; hour != lastHourToCheck; hour = (hour + 1) % 24)
        {
            List<String> minutes = timetable.getMinutes(hour);
            if (!minutes.isEmpty())
            {
                int minute = parseMinuteStringToInt(minutes.get(0));
                firstDeparture = LocalTime.of(hour, minute);
                break;
            }
        }
        return firstDeparture;
    }

    private Integer parseMinuteStringToInt(String minuteString)
    {
        String str = minuteString.substring(0, 2);
        return Integer.parseInt(str);
    }
}
