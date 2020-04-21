package com.satendra;

public enum ExitStatus {

    SUCCESS_RESULT_VALUE(0),

    ERROR_RESULT_VALUE(1);

    private final int exitCode;

    ExitStatus(int exitCode) {
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }
}
