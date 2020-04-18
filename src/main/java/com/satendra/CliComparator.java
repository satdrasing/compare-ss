package com.satendra;


import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CliComparator {

    private int result;

    private InputStreamSupplier createdStreamSupplier;

    private  String inputFileExtention;

    private String copiedBsddid;

    ElementReplace elementReplace;

    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            readFile(cliArguments.getCreatedBsddId().get(), cliArguments.getCopiedBsddId().get());
            //writeFile
            writeFile(copiedBsddid);
        }
    }

    public void readFile(String createdbsddid, String copiedBsddid) throws IOException {
        createdStreamSupplier = () -> Files.newInputStream(Paths.get(createdbsddid));
        inputFileExtention = extractFileExtention(createdbsddid);

        this.copiedBsddid =copiedBsddid;

         elementReplace = new ElementReplace(createdStreamSupplier, copiedBsddid);

        elementReplace.readJsonTree();
    }

    public void writeFile(String copiedBsddid) throws IOException {
        JsonDiskWriter jsonDiskWriter = new JsonDiskWriter(elementReplace.replacedJsonTree, copiedBsddid,inputFileExtention);
        jsonDiskWriter.Write();
    }

     private String extractFileExtention(String fullFileName){
        return FilenameUtils.getExtension(fullFileName);
    }
    public int getResult() {
        return result;
    }
}
