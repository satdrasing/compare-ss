package com.satendra;


import java.io.IOException;

public class CliComparator {

    private int result;

    private ElementReplace elementReplace;

    public static final int SUCCESS_RESULT_VALUE = 0;
    public static final int ERROR_RESULT_VALUE = 1;

    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            result = compare(cliArguments.getCreatedBsddId().get(), cliArguments.getCopiedBsddId().get());
            writeJsonDisK(cliArguments);
        }
    }

    private void writeJsonDisK(CliArguments cliArguments) throws IOException {

        if(result ==SUCCESS_RESULT_VALUE){
            if (cliArguments.getOutputFile().isPresent()) {
                elementReplace.writeTo(cliArguments.getOutputFile().get());
            } else {
                elementReplace.writeFile();
            }
        }
    }

    private int compare(String createdbsddid, String copiedBsddid) {

        try {
            elementReplace = new ElementReplace(createdbsddid, copiedBsddid);
            elementReplace.readJsonTree();

            return SUCCESS_RESULT_VALUE;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ERROR_RESULT_VALUE;
        }
    }

    public int getResult() {
        return result;
    }
}
