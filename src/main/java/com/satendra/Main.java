package com.satendra;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println(".............MoeVe...............  ");

        final CliArguments cliArguments = new CliArguments(args);
        if (cliArguments.areAvailable()) {
            System.exit(startCLI(cliArguments));
        } else if (cliArguments.isHelp()) {
            cliArguments.printHelp();
        } else {
            cliArguments.printHelp();
        }
    }

    private static int startCLI(CliArguments cliArguments) throws IOException {
        return new CliComparator(cliArguments).getResult();
    }
}
