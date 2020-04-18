package com.satendra;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;


public class JsonWriter {

    private final JsonNode jsonString;

    private  String outFileName;

    final ObjectWriter objectWriter;

    public JsonWriter(JsonNode jsonString, String outFileName) {
        this.jsonString = jsonString;
        this.outFileName = outFileName;
        this.objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());
    }

    public void Write() throws IOException {
        objectWriter.writeValue(new File(outFileName), jsonString);
        System.out.println("File created.....................  " + outFileName);
    }
}
