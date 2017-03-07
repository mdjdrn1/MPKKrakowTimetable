package com.github.mdjdrn1.MPKKrakowTimetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeParser implements IParser
{
    public List<Integer> getLinesNumbersList()
    {
        List<Integer> linesList = new ArrayList<>();
        linesList.addAll(Arrays.asList(1, 2, 3, 4, 5));
        return linesList;
    }
}

