package com.show.tree.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
