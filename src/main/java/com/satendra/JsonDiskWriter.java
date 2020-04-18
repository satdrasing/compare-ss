package com.satendra;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;


public class JsonDiskWriter {

    private final JsonNode jsonString;

    private  String outFileName;

    private String fileExtention;

    final ObjectWriter objectWriter;

    public JsonDiskWriter(JsonNode jsonString, String outFileName, String fileExtention) {
        this.jsonString = jsonString;
        this.outFileName = outFileName;
        this.fileExtention =fileExtention;
        this.objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());

    }

    public void Write() throws IOException {
        objectWriter.writeValue(new File(outFileName+"."+fileExtention), jsonString);
        System.out.println("File created.....................  " + outFileName);
    }




}
