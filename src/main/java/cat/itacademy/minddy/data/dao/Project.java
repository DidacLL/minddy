package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.trackers.ProjectTracker;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.interfaces.Notable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_project_state",columnList ="state")
})
@NoArgsConstructor
@Getter
@Setter
public class Project implements Notable {
    @EmbeddedId
    private  HierarchicalId id;
    @Column(nullable = false,columnDefinition = "VARCHAR(60)")
    private String name;
    private String description;
    @Enumerated @Column(nullable = false,name = "state")
    private ProjectState state = ProjectState.ACTIVE;
    private LocalDate deadLine=null;
    @ManyToMany
    private List<Tag> tags=new ArrayList<>();
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Task> tasks=new ArrayList<>();
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private List<Note> notes=new ArrayList<>();
//    @Convert(converter = ProjectTrackerListConverter.class)
//    @Column(columnDefinition = "JSON")
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
    private List<ProjectTracker> trackers;
    private String uiConfig="";
    @Embedded
    @Column
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
