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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElementReplace {

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

        Stream.of(SearchId.values()).map(Enum::toString)
                .collect(Collectors.toList())
                .forEach(ids -> change(replacedJsonTree, ids, copiedBsddid));

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

    public void writeFile() throws IOException {
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
