package ro.msg.learning.shop.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonConverter {
    private JsonConverter() {

    }

    public static String toJson(Object element) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        ObjectWriter objectWriter = mapper.writer();
        try {
            return objectWriter.writeValueAsString(element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
