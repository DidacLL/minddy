package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProjectData {
    private String ownerID;
    private String projectID;
    private String projectName;
    private String description;
    private String state;
    private LocalDate deadline;
    private String uiConfig;
    private Tag[] tags;

    public static ProjectData fromEntity(Project entity){


        return new ProjectData(
                entity.getId().getHolderId(),
                entity.getId().getOwnId(),
                entity.getName(),
                entity.getDescription(),
                entity.getState().name(),
                entity.getDeadLine(),
                entity.getUiConfig(),
                entity.getTags().toArray(Tag[]::new)
        );


    }
    public static ProjectData fromDTO(ProjectDTO dto){
        return new ProjectData(
                dto.getId().getHolderId(),
                dto.getId().getOwnId(),
                dto.getName(),
                dto.getDescription(),
                dto.getState().name(),
                dto.getDeadLine(),
                dto.getUiConfig(),
                new ArrayList<Tag>().toArray(Tag[]::new));
    }
}
