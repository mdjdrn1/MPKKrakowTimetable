package com.github.mdjdrn1.MPKKrakowTimetable.lines;

public class ParsingException extends Throwable
{
    private final static String defaultMessage = "Error occurred during parsing.";

    public ParsingException()
    {
        this(defaultMessage);
    }

    public ParsingException(String message)
    {
        super(message);
    }
}
