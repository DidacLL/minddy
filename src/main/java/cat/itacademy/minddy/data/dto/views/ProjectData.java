package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProjectData {
    private String ownerID;
    private String projectID;
    private String projectName;
    private String description;
    private ProjectState state;
    private LocalDate deadLine;
    private String uiConfig;

    public static ProjectData fromEntity(Project entity){
        return new ProjectData(
                entity.getId().getHolderId(),
                entity.getId().getOwnId(),
                entity.getName(),
                entity.getDescription(),
                entity.getState(),
                entity.getDeadLine(),
                entity.getUiConfig()
        );
    }
    public static ProjectData fromDTO(ProjectDTO dto){
        return new ProjectData(
                dto.getId().getHolderId(),
                dto.getId().getOwnId(),
                dto.getName(),
                dto.getDescription(),
                dto.getState(),
                dto.getDeadLine(),
                dto.getUiConfig()
        );
    }
}
