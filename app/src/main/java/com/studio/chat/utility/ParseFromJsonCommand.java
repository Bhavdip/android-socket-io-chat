package com.studio.chat.utility;

import android.text.TextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ParseFromJsonCommand {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String mJson;
    private Class<?> classToDeserializeTo;

    public ParseFromJsonCommand(String json, Class<?> target){
            this.mJson = json;
            this.classToDeserializeTo = target;
    }

    public <T> T buildList() {
        if(TextUtils.isEmpty(mJson)){
            return null;
        }

        try {
            return objectMapper.readValue(mJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Class.forName(classToDeserializeTo.getName())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object buildObject(){
        if(TextUtils.isEmpty(mJson)){
            return null;
        }

        try {
            return objectMapper.readValue(mJson,Class.forName(classToDeserializeTo.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
