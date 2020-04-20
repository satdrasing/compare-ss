package com.satendra;


import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CliComparator {

    private int result;
    
    ElementReplace elementReplace;

    public static final int SUCESS_RESULT_VALUE = 0;
    public static final int ERROR_RESULT_VALUE = 1;

    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            result = compare(cliArguments.getCreatedBsddId().get(), cliArguments.getCopiedBsddId().get());

        }
    }

    private int compare(String createdbsddid, String copiedBsddid) {

        try {
            elementReplace = new ElementReplace(createdbsddid, copiedBsddid);
            elementReplace.readJsonTree();
            elementReplace.writeFile();
            return SUCESS_RESULT_VALUE;
        } catch (Exception e) {
            System.err.println("Error"+e.getMessage());
            return ERROR_RESULT_VALUE;
        }
    }

    public int getResult() {
        return result;
    }
}
