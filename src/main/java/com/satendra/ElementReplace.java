package com.satendra;

import com.fasterxml.jackson.core.JsonParseException;
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

    JsonNode originalJsonTree ;

    JsonNode replacedJsonTree;

    private final InputStreamSupplier createdStreamSupplier;

    public ElementReplace(InputStreamSupplier createdStreamSupplier, String coppiedId) {
        this.createdStreamSupplier = createdStreamSupplier;
        this.coppiedId = coppiedId;
        mapper = new ObjectMapper();
    }

    public void readJsonTree() throws IOException {

            originalJsonTree = mapper.readTree(createdStreamSupplier.get());

            replacedJsonTree = mapper.readTree(createdStreamSupplier.get());

            change(replacedJsonTree, BSSDID, coppiedId);
            change(replacedJsonTree, PERSONEN_ID, coppiedId);
            change(replacedJsonTree, KASSENZEICHEN, coppiedId);

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
