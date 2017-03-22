package com.github.mdjdrn1.MPKKrakowTimetable.serializable;

import com.github.mdjdrn1.MPKKrakowTimetable.ILine;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Direction;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Stop;
import com.github.mdjdrn1.MPKKrakowTimetable.structures.Timetable;

import java.util.ArrayList;
import java.util.List;

public class SerializableLineBuilder
{
    private SerializableLine sLine;

    // TODO: THAT'S ONLY DRAFT!
    // TODO: refactor (split to methods)
    public SerializableLineBuilder(ILine line) throws Exception
    {
        int lineNumber = line.getLineNumber();

        sLine = new SerializableLine();
        sLine.setLine(new SLine());
        sLine.getLine().setNumber(lineNumber);

        List<SCourse> courses = new ArrayList<>();

        List<Direction> directions = line.getDirectionsList();
        // setting up courses
        for (Direction direction : directions)
        {
            SCourse course = new SCourse();

            // setting up direction
            course.setDirection(direction.getName());

            List<Stop> stopsList = line.getStopsList(direction);

            // setting up first stop timetable
            List<Timetable> firstSStopTimetable = line.getTimetables(direction, stopsList.get(0));

            List<STimetable> firstSStopSTimetable = new ArrayList<>();
            for(Timetable timetable : firstSStopTimetable)
            {
                STimetable newTimetable = new STimetable();

                newTimetable.setDescription(timetable.getDescription());
                newTimetable.setHours(timetable.getTimetable());

                firstSStopSTimetable.add(newTimetable);
            }
            course.setTimetable(firstSStopSTimetable);

            // setting up stops list
            List<Integer> delayList = line.getDelayList(direction);

            List<SStop> sStopList = new ArrayList<>();
            for (int i = 0; i < stopsList.size() - 1; ++i)
            {
                SStop newStop = new SStop();
                newStop.setId(stopsList.get(i + 1).getId());
                newStop.setName(stopsList.get(i + 1).getName());
                newStop.setOnDemand(stopsList.get(i + 1).isOnDemand());
                newStop.setDelay(delayList.get(i));

                sStopList.add(newStop);
            }

            course.setStop(sStopList);

            courses.add(course);
        }
        sLine.getLine().setCourse(courses);
    }

    public SerializableLine getSerializableLine()
    {
        return sLine;
    }
}
