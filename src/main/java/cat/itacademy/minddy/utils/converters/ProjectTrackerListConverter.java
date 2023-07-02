package cat.itacademy.minddy.utils.converters;

import cat.itacademy.minddy.data.dao.trackers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
@Converter
public class ProjectTrackerListConverter implements AttributeConverter<List<ProjectTracker>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ProjectTracker> attribute) {
        try {
            List<JsonNode> jsonNodes = new ArrayList<>();
            for (ProjectTracker tracker : attribute) {
                JsonNode jsonNode = objectMapper.valueToTree(tracker);
                ((ObjectNode) jsonNode).put("type", tracker.getClass().getSimpleName());
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
    public List<ProjectTracker> convertToEntityAttribute(String dbData) {
        try {
            List<ProjectTracker> trackers = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(dbData);
            for (JsonNode node : jsonNode) {
                String type = node.get("type").asText();
                Class<? extends ProjectTracker> trackerClass = getTrackerClass(type);
                if (trackerClass != null) {
                    ProjectTracker tracker = objectMapper.treeToValue(node, trackerClass);
                    trackers.add(tracker);
                }
            }
            return trackers;
        } catch (JsonProcessingException e) {
            // TODO: 21/06/2023   Manejar el error de deserialización
            e.printStackTrace();
        }
        return null;
    }

    private Class<? extends ProjectTracker> getTrackerClass(String type) {
        return switch (type) {
            case "StreakTracker" -> StreakTracker.class;
            case "QualificationTracker" -> QualificationTracker.class;
            case "CounterTracker" -> CounterTracker.class;
            case "ResumeTracker" -> ResumeTracker.class;
            case "SprintTracker" -> SprintTracker.class;
            default -> null;
        };
    }
}
