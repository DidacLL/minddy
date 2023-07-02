package cat.itacademy.minddy.utils.converters;

import cat.itacademy.minddy.data.dao.SubTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class SubTaskListConverter implements AttributeConverter<List<SubTask>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<SubTask> attribute) {
        try {
            List<JsonNode> jsonNodes = new ArrayList<>();
            for (SubTask subTask : attribute) {
                JsonNode jsonNode = objectMapper.valueToTree(subTask);
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
    public List<SubTask> convertToEntityAttribute(String dbData) {
        try {
            List<SubTask> subTasks = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(dbData);
            for (JsonNode node : jsonNode) {
                    SubTask subTask = objectMapper.treeToValue(node, SubTask.class);
                    subTasks.add(subTask);
            }
            return subTasks;
        } catch (JsonProcessingException e) {
            // TODO: 21/06/2023   Manejar el error de deserialización
            e.printStackTrace();
        }
        return null;
    }
}
