package com.satendra;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;


public class JsonDiskWriter {

    private final JsonNode jsonString;

    private final String outFileName;

    private final String fileExtension;

    final ObjectWriter objectWriter;

    public JsonDiskWriter(JsonNode jsonString, String outFileName, String fileExtension) {
        this.jsonString = jsonString;
        this.outFileName = outFileName;
        this.fileExtension = fileExtension;
        this.objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());

    }

    public void Write() throws IOException {

        String fileName = outFileName + "." + fileExtension;
        objectWriter.writeValue(new File(fileName), jsonString);
        System.out.println("Output file name .....................  :" + fileName);
    }


}
