package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.TaskDTO;

import java.util.List;

public class TaskFullView {
    TaskDTO dto;
    List<TagDTO> tags;
    HierarchicalId ownerId;
}
