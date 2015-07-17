package com.studio.chat.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ParseFromJsonCommand<ObjectType extends Object> {
    private final String jsonString;
    private final Class<ObjectType> classToDeserializeTo;
    private ObjectMapper mObjectMapper = new ObjectMapper();

    public ParseFromJsonCommand(String aJsonString, Class<ObjectType> aClassToDeserializeTo) {
        jsonString = aJsonString;
        classToDeserializeTo = aClassToDeserializeTo;
    }

    public ObjectType execute() {
        try {
            return mObjectMapper.readValue(jsonString, classToDeserializeTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
