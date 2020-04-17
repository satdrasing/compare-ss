package com.satendra;

public class CliArgumentsParseException extends RuntimeException {
    public CliArgumentsParseException(Exception cause) {
        super("Failed to process Command Line Arguments.", cause);
    }
}
