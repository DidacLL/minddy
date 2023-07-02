package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dao.trackers.ProjectTracker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private HierarchicalId id;
    private String name;
    private String description;
    private ProjectState state;
    private LocalDate deadLine;
    private List<ProjectTracker> trackers;
    private String uiConfig;
    private List<Tag> tags;

    public static ProjectDTO fromEntity(Project entity){
        return new ProjectDTO().setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setState(entity.getState())
                .setDeadLine(entity.getDeadLine())
                .setTrackers(entity.getTrackers())
                .setUiConfig(entity.getUiConfig())
                .setTags(entity.getTags());
    }
    public static ProjectDTO createRootProject(UUID userId,String userName,String uiConfig){
        ArrayList<Tag> tag = new ArrayList<>();
        tag.add(new Tag().setVisible(false).setId(
                new TagId(userId,"root")
        ).setHeritable(false));
        return new ProjectDTO().setId(
                new HierarchicalId().setOwnId("01").setUserId(userId).setHolderId("")
        ).setTags(tag).setName(userName).setDescription("").setState(ProjectState.ACTIVE)
                .setTrackers(
// TODO: 02/07/2023  Add trackers to count subprojects and tasks
                        null
                )
                .setUiConfig(uiConfig).setDeadLine(null);
    }
//    public ProjectDTO newSubProject(String name, ProjectState state, String description, String uiConfig, List<ProjectTracker> trackers, Tag... tags){
////        var newTags= new ArrayList<Tag>(List.of(tags));
////        this.tags.stream().filter(Tag::isHeritable).forEach(newTags::add);
////        return new ProjectDTO().setId(
////                new HierarchicalId()
////                        .setHolderId(this.id.toString())
////                        .setUserId(this.id.getUserId())
////                        .setOwnId(Integer.toHexString(0xFF - this.subProjectCounter++))
////        )
////                .setName(name)
////                .setTags(newTags)
////                .setState(state)
////                .setSubProjectCounter(0)
////                .setDescription(description)
////                .setUiConfig(uiConfig)
////                .setTrackers(trackers);
//    }

}
