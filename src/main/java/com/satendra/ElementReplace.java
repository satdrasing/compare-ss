package com.satendra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ElementReplace {

    private static final Logger LOG = LoggerFactory.getLogger(ElementReplace.class);

    public static final String BSSDID = "bsddid";

    public static final String PERSONEN_ID = "personenID";

    public  static final String KASSENZEICHEN = "kassenzeichen";
    private final ObjectMapper mapper;

    private final String coppiedId;

    private final InputStreamSupplier createdStreamSupplier;

    public ElementReplace(InputStreamSupplier createdStreamSupplier, String coppiedId) {
        this.createdStreamSupplier = createdStreamSupplier;
        this.coppiedId = coppiedId;
        mapper = new ObjectMapper();
    }

    public void readJsonTree() throws IOException {
        JsonNode tree = mapper.readTree(createdStreamSupplier.get());
        change(tree, BSSDID, coppiedId);
        change(tree, PERSONEN_ID, coppiedId);
        change(tree,KASSENZEICHEN,coppiedId);
        JsonWriter jsonWritter = new JsonWriter(tree,coppiedId);
        jsonWritter.Write();
    }

    private void change(JsonNode parent, String fieldName, String newValue) {
        if (parent.has(fieldName)) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }
        // Now, recursively invoke this method on all properties
        for (JsonNode child : parent) {
            change(child, fieldName, newValue);
        }
    }
}
