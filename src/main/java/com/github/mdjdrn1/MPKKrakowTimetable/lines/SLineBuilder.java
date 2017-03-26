package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.ArrayList;
import java.util.List;

public class SLineBuilder implements ISLineBuilder
{
    private ILine iLine;

    public SLineBuilder(ILine line)
    {
        iLine = line;
    }

    @Override
    public int buildLineNumber()
    {
        return iLine.getLineNumber();
    }

    @Override
    public List<SCourse> buildCourses() throws ParsingException, ConnectionError
    {

        List<SCourse> courses = new ArrayList<>();

        List<Direction> directions = iLine.getDirectionsList();
        for (Direction direction : directions)
        {
            List<Stop> stopsList = iLine.getStopsList(direction);

            SCourse course = new SCourse();

            // setting up direction
            course.setDirection(direction.getName());

            // setting up first stop timetable
            course.setTimetable(buildFirstStopTimetable(direction, stopsList));

            // setting up stops list
            course.setStop(buildStopsList(direction, stopsList));

            courses.add(course);
        }

        return courses;
    }

    private List<STimetable> buildFirstStopTimetable(Direction direction, List<Stop> stopsList) throws ParsingException, ConnectionError
    {
        List<Timetable> firstStopTimetable = iLine.getTimetables(direction, stopsList.get(0));

        List<STimetable> newFirstStopTimetable = new ArrayList<>();
        for(Timetable timetable : firstStopTimetable)
        {
            STimetable newTimetable = new STimetable();

            newTimetable.setDescription(timetable.getDescription());
            newTimetable.setHours(timetable.getTimetable());

            newFirstStopTimetable.add(newTimetable);
        }

        return newFirstStopTimetable;
    }

    private List<SStop> buildStopsList(Direction direction, List<Stop> stopsList) throws ParsingException, ConnectionError
    {
        List<Integer> delayList = iLine.getDelayList(direction);

        List<SStop> newStopsList = new ArrayList<>();
        for (int i = 0; i < stopsList.size(); ++i)
        {
            SStop newStop = new SStop();
            newStop.setId(stopsList.get(i).getId());
            newStop.setName(stopsList.get(i).getName());
            newStop.setOnDemand(stopsList.get(i).isOnDemand());
            newStop.setDelay(delayList.get(i));

            newStopsList.add(newStop);
        }

        return newStopsList;
    }
}
