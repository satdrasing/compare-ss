package com.satendra;


import java.io.IOException;

public class CliComparator {

    private ElementReplace elementReplace;

    private ExitStatus exitStatus;

    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            exitStatus = compare(cliArguments.getCreatedBsddId().get(), cliArguments.getCopiedBsddId().get());
            writeJsonDisK(cliArguments);
        }
    }

    private void writeJsonDisK(CliArguments cliArguments) throws IOException {

        if(exitStatus ==ExitStatus.SUCCESS_RESULT_VALUE){
            if (cliArguments.getOutputFile().isPresent()) {
                elementReplace.writeTo(cliArguments.getOutputFile().get());
            } else {
                elementReplace.writeFile();
            }
        }
    }

    private ExitStatus compare(String createdbsddid, String copiedBsddid) {

        try {
            elementReplace = new ElementReplace(createdbsddid, copiedBsddid);
            elementReplace.readJsonTree();

            return ExitStatus.SUCCESS_RESULT_VALUE;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ExitStatus.ERROR_RESULT_VALUE;
        }
    }

    public ExitStatus getResult() {
        return exitStatus;
    }
}
