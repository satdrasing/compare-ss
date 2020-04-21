package com.satendra;


import com.satendra.exception.CliArgumentsParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            final CliArguments cliArguments = new CliArguments(args);
            if (cliArguments.areAvailable()) {
                System.exit(startCLI(cliArguments).getExitCode());
            } else if (cliArguments.isHelp()) {
                cliArguments.printHelp();
            } else {
                cliArguments.printHelp();
            }
        } catch (CliArgumentsParseException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static ExitStatus startCLI(CliArguments cliArguments) throws IOException {
        return new CliComparator(cliArguments).getResult();
    }
}
