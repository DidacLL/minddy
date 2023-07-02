package cat.itacademy.minddy.utils.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            List<JsonNode> jsonNodes = new ArrayList<>();
            for (String str : attribute) {
                JsonNode jsonNode = objectMapper.valueToTree(str);
                jsonNodes.add(jsonNode);
            }
            return objectMapper.writeValueAsString(jsonNodes);
        } catch (JsonProcessingException e) {
            // TODO: 21/06/2023   Manejar el error de serialización
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            List<String> list = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(dbData);
            for (JsonNode node : jsonNode) {
                    String str = objectMapper.treeToValue(node, String.class);
                    list.add(str);
            }
            return list;
        } catch (JsonProcessingException e) {
            // TODO: 21/06/2023   Manejar el error de deserialización
            e.printStackTrace();
        }
        return null;
    }
}
