package com.satendra;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CliComparator {

    private int result;

    private InputStreamSupplier createdStreamSupplier;


    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            readFile(cliArguments.getCreatedBsddId().get(), cliArguments.getCopiedBsddId().get());
        }
    }

    public void readFile(String createdbsddid, String copiedBsddid) throws IOException {
        createdStreamSupplier = () -> Files.newInputStream(Paths.get(createdbsddid));
        ElementReplace elementReplace = new ElementReplace(createdStreamSupplier, copiedBsddid);
        elementReplace.readJsonTree();
    }


    public int getResult() {
        return result;
    }
}
