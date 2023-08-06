package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dao.trackers.ProjectTracker;
import cat.itacademy.minddy.data.interfaces.Taggable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO implements Taggable {
    private HierarchicalId id;
    private String name;
    private String description;
    private ProjectState state;
    private LocalDate deadLine;
    private List<ProjectTracker> trackers;
    private String uiConfig;
    private List<Tag> tags;

    public boolean isFullFilled() {
        return (id != null && id.getUserId() != null && id.getHolderId() != null && name != null && state != null);
    }

    public static ProjectDTO fromEntity(Project entity) {
        return new ProjectDTO().setId(entity.getId()).setName(entity.getName()).setDescription(entity.getDescription()).setState(entity.getState()).setDeadLine(entity.getDeadLine()).setTrackers(entity.getTrackers()).setUiConfig(entity.getUiConfig()).setTags(entity.getTags());
    }


    @Override
    public ProjectDTO addTag(Tag tag) {
        if(this.tags==null)setTags(List.of(tag));
        else if(!this.tags.contains(tag)){
            this.tags.add(tag);
        }
        return this;
    }
}
