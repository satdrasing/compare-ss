package com.satendra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.github.fge.jsonpatch.diff.JsonDiff;

import java.io.IOException;

public class ElementReplace {

    public static final String BSSDID = "bsddid";

    public static final String PERSONEN_ID = "personenID";

    public static final String KASSENZEICHEN = "kassenzeichen";

    private final ObjectMapper mapper;

    private final String coppiedId;

    private final InputStreamSupplier createdStreamSupplier;

    public ElementReplace(InputStreamSupplier createdStreamSupplier, String coppiedId) {
        this.createdStreamSupplier = createdStreamSupplier;
        this.coppiedId = coppiedId;
        mapper = new ObjectMapper();
    }

    public void readJsonTree() throws IOException {

        JsonNode originalJsonTree = mapper.readTree(createdStreamSupplier.get());

        JsonNode replacedJsonTree = mapper.readTree(createdStreamSupplier.get());

        change(replacedJsonTree, BSSDID, coppiedId);
        change(replacedJsonTree, PERSONEN_ID, coppiedId);
        change(replacedJsonTree, KASSENZEICHEN, coppiedId);
        JsonWriter jsonWriter = new JsonWriter(replacedJsonTree, coppiedId);
        jsonWriter.Write();
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
}
