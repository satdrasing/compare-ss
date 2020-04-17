package com.satendra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CliComparator {

    private int result;

    private InputStreamSupplier createdStreamSupplier;


    public CliComparator(CliArguments cliArguments) throws IOException {

        if (cliArguments.getCreatedBsddId().isPresent() && cliArguments.getCopiedBsddId().isPresent()) {
            readFile(cliArguments.getCreatedBsddId().get(),cliArguments.getCopiedBsddId().get());
        }
    }

    public  void  readFile(String createdbsddid, String copiedBsddid) throws IOException {
        createdStreamSupplier =() -> Files.newInputStream(Paths.get(createdbsddid));
        ElementReplace elementReplace = new ElementReplace(createdStreamSupplier, copiedBsddid);
        elementReplace.readJsonTree();
    }

    public  void change(JsonNode parent, String fieldName, String newValue) {
        if (parent.has(fieldName)) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }

        // Now, recursively invoke this method on all properties
        for (JsonNode child : parent) {
            change(child, fieldName, newValue);
        }
    }


    public int getResult() {
        return result;
    }
}
