package com.github.mdjdrn1.MPKKrakowTimetable.lines;

import java.util.ArrayList;
import java.util.List;

public class SerializableLineBuilder
{
    private SerializableLine sLine;

    public SerializableLineBuilder(ILine line) throws Exception
    {
        int lineNumber = line.getLineNumber();

        sLine = new SerializableLine();
        sLine.setLine(new SLine());
        sLine.getLine().setNumber(lineNumber);
        sLine.getLine().setCourse(setupCourses(line));
    }

    private List<SCourse> setupCourses(ILine line) throws Exception
    {

        List<SCourse> courses = new ArrayList<>();

        List<Direction> directions = line.getDirectionsList();
        for (Direction direction : directions)
        {
            List<Stop> stopsList = line.getStopsList(direction);

            SCourse course = new SCourse();

            // setting up direction
            course.setDirection(direction.getName());

            // setting up first stop timetable
            course.setTimetable(setupFirstStopTimetable(line, direction, stopsList));

            // setting up stops list
            course.setStop(setupStopsList(line, direction, stopsList));

            courses.add(course);
        }

        return courses;
    }

    private List<STimetable> setupFirstStopTimetable(ILine line, Direction direction, List<Stop> stopsList) throws Exception
    {
        List<Timetable> firstStopTimetable = line.getTimetables(direction, stopsList.get(0));

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

    private List<SStop> setupStopsList(ILine line, Direction direction, List<Stop> stopsList) throws Exception
    {
        List<Integer> delayList = line.getDelayList(direction);

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

    public SerializableLine getSerializableLine()
    {
        return sLine;
    }
}
