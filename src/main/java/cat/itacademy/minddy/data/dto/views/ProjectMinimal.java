package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.ProjectState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectMinimal {
    private String ownerID;
    private String projectID;
    private String projectName;
    private int notes;
    private int pendingTasks;
    private ProjectState state;

    private String uiConfig;

    public ProjectMinimal(String ownerID, String projectID, String projectName, Long notes, Long pendingTasks, String uiConfig, ProjectState state ) {
        this.ownerID = ownerID;
        this.projectID = projectID;
        this.projectName = projectName;
        this.notes = notes != null ? notes.intValue() : 0;
        this.pendingTasks = pendingTasks != null ? pendingTasks.intValue() : 0;
        this.uiConfig = uiConfig;
        this.state=state;
    }

}
