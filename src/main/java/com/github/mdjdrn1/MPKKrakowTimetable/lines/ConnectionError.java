package com.github.mdjdrn1.MPKKrakowTimetable.lines;

public class ConnectionError extends Throwable
{
    private final static String defaultMessage = "Error occurred during parsing.";

    public ConnectionError()
    {
        this(defaultMessage);
    }

    public ConnectionError(String message)
    {
        super(message);
    }
}
