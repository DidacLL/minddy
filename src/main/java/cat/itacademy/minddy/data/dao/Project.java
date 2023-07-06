package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.trackers.ProjectTracker;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.utils.converters.ProjectTrackerListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_userHierarchy",columnList ="user_id,holder_id")
})
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @EmbeddedId
    private  HierarchicalId id;
    private String name;
    private String description;
    @Enumerated
    private ProjectState state;
    private LocalDate deadLine;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Task> tasks;
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Note> content;
    @Convert(converter = ProjectTrackerListConverter.class)
    @Column(columnDefinition = "JSON")
    private List<ProjectTracker> trackers;
    private String uiConfig;
    @Embedded
    private DateLog dateLog;

public static Project fromDTO(ProjectDTO dto){
    return new Project().setId(dto.getId())
            .setName(dto.getName())
            .setDescription(dto.getDescription())
            .setState(dto.getState())
            .setDeadLine(dto.getDeadLine())
            .setTrackers(dto.getTrackers())
            .setUiConfig(dto.getUiConfig())
            .setTags(dto.getTags());

}
}
