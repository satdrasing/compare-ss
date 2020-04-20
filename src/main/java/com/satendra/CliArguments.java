package com.satendra;

import com.satendra.exception.CliArgumentsParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

public class CliArguments {

    private final Options options;
    private CommandLine commandLine;

    private static final int CREATED_INDEX = 0;
    private static final int COPIED_INDEX = 1;

    private static final String HELP_OPTION = "h";
    private static final String HELP_LONG_OPTION = "help";

    private static final String OUTPUT_OPTION = "o";
    private static final String OUTPUT_LONG_OPTION = "output";

    public CliArguments(String[] args) {
        options = new Options();
        options.addOption(buildHelpOption());

        process(args);
    }

    private Option buildHelpOption() {

        return Option.builder(HELP_OPTION)
                .argName("help")
                .desc("Display text and exit")
                .hasArg(false)
                .longOpt(HELP_LONG_OPTION)
                .numberOfArgs(0)
                .required(false)
                .build();
    }

    public Optional<String> getOutputFile() {
        if (commandLine.hasOption(OUTPUT_OPTION)) {
            return Optional.empty();
        }
        return Optional.of(commandLine.getOptionValue(OUTPUT_OPTION));
    }

    private Option buildOutputOption() {
        return Option.builder(OUTPUT_OPTION)
                .argName("output")
                .desc("provide an option for output file for the result")
                .hasArg(true)
                .longOpt(OUTPUT_LONG_OPTION)
                .numberOfArgs(1)
                .required(false)
                .type(String.class)
                .valueSeparator('=')
                .build();
    }

    private void process(String[] args) {
        try {
            CommandLineParser commandLineParser = new DefaultParser();
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException exception) {
            throw new CliArgumentsParseException(exception);
        }
    }

    public Boolean areAvailable() {
        return commandLine.getArgList().size() > 1;
    }

    public Boolean isHelp() {
        return commandLine.hasOption(HELP_OPTION);
    }

    public Optional<String> getCreatedBsddId() {
        return getRemainingArgument(CREATED_INDEX);
    }

    public Optional<String> getCopiedBsddId() {
        return getRemainingArgument(COPIED_INDEX);
    }

    private Optional<String> getRemainingArgument(int index) {
        if (commandLine.getArgList().isEmpty() || commandLine.getArgList().size() < index + 1) {
            return Optional.empty();
        }
        return Optional.of(commandLine.getArgList().get(index));
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar bbsidcompare-1.0-SNAPSHOT-full.jar [created] [copied]", options);
    }
}
