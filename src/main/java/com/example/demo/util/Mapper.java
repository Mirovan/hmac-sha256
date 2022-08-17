package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Mapper<T> {
    public T jsonToObject(String json, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
