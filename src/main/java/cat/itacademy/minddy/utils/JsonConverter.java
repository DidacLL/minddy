//package cat.itacademy.minddy.utils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.io.IOException;
//import java.io.Serializable;
//
//// TODO: 18/06/2023
//@Converter
//public class JsonConverter<T> implements AttributeConverter<T, String> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(T attribute) {
//        try {
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            // TODO: 18/06/2023 Manage EXCEPTION
//            return null;
//        }
//    }
//
//    @Override
//    public T convertToEntityAttribute(String dbData) {
//        try {
//            return objectMapper.readValue(dbData, this.getClass().getComponentType());
//        } catch (IOException e) {
//            // TODO: 18/06/2023 Manage EXCEPTION
//            return null;
//        }
//    }
//}
