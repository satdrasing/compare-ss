package com.satendra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.diff.JsonDiff;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ElementReplace {

    public static final String BSSDID = "bsddid";
    public static final String PERSONEN_ID = "personenID";
    public static final String KASSENZEICHEN = "kassenzeichen";

    private final ObjectMapper mapper = new ObjectMapper();

    private JsonNode replacedJsonTree;

    final private String inputFileExtension;

    final private String copiedBsddid;

    private final InputStreamSupplier createdStreamSupplier;

    public ElementReplace(String createdbsddid, String copiedBsddid) {
        Objects.requireNonNull(createdbsddid, "created id is null");
        Objects.requireNonNull(copiedBsddid, "copied id is null");

        this.createdStreamSupplier = () -> Files.newInputStream(Paths.get(createdbsddid));
        this.inputFileExtension = extractFileExtension(createdbsddid);
        this.copiedBsddid = copiedBsddid;
    }

    public void readJsonTree() throws IOException {
        JsonNode originalJsonTree = mapper.readTree(createdStreamSupplier.get());
        replacedJsonTree = mapper.readTree(createdStreamSupplier.get());

        change(replacedJsonTree, BSSDID, copiedBsddid);
        change(replacedJsonTree, PERSONEN_ID, copiedBsddid);
        change(replacedJsonTree, KASSENZEICHEN, copiedBsddid);

        JsonNode diff = JsonDiff.asJson(originalJsonTree, replacedJsonTree);

        System.out.println(diff.toPrettyString());
    }

    private void change(JsonNode parent, String fieldName, String newValue) {

        if (parent.has(fieldName)) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }

        for (JsonNode child : parent) {
            change(child, fieldName, newValue);
        }
    }

    public void  writeFile() throws IOException {
        JsonDiskWriter jsonDiskWriter = new JsonDiskWriter(replacedJsonTree, copiedBsddid, inputFileExtension);
        jsonDiskWriter.write();
        createdStreamSupplier.get().close();

    }

    public void writeTo(String directoryPath) throws IOException {
        JsonDiskWriter jsonDiskWriter = new JsonDiskWriter(replacedJsonTree, copiedBsddid, inputFileExtension, directoryPath);
        jsonDiskWriter.writeToLocation();
        createdStreamSupplier.get().close();
    }

    private String extractFileExtension(String fullFileName) {
        return FilenameUtils.getExtension(fullFileName);
    }
}
