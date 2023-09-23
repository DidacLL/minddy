package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.views.ProjectData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private HierarchicalId id;
    private String name;
    private String description;
    private ProjectState state=ProjectState.ACTIVE;
    private LocalDate deadLine;
//    private List<ProjectTracker> trackers;
    private String uiConfig;
//    private List<Tag> tags;

    public boolean isFullFilled() {
        return (id != null && id.getUserId() != null && id.getHolderId() != null && name != null && state != null);
    }

    public static ProjectDTO fromEntity(Project entity) {
        return new ProjectDTO().setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setState(entity.getState())
                .setDeadLine(entity.getDeadLine())
                .setUiConfig(entity.getUiConfig());
    }
    public static ProjectDTO fromData(String userId, ProjectData data) {
        return new ProjectDTO().setId(new HierarchicalId(userId,data.getOwnerID(),data.getProjectID()))
                .setName(data.getProjectName())
                .setDescription(data.getDescription())
                .setState(data.getState())
                .setDeadLine(data.getDeadLine())
                .setUiConfig(data.getUiConfig());
    }


}
