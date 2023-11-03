package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.trackers.ProjectTracker;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.interfaces.Notable;
import cat.itacademy.minddy.data.interfaces.Taggable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_project_state", columnList = "state")
})
@NoArgsConstructor
@Getter
@Setter
public class Project implements Notable, Taggable<Project> {
    @EmbeddedId
    private HierarchicalId id;
    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    private String name;
    private String description;
    @Enumerated
    @Column(nullable = false, name = "state")
    private ProjectState state = ProjectState.ACTIVE;
    private LocalDate deadLine = null;
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();
    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();
    //    @Convert(converter = ProjectTrackerListConverter.class)
//    @Column(columnDefinition = "JSON")
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<ProjectTracker> trackers;
    @Column(columnDefinition = "TEXT")
    private String uiConfig = "";
    @Embedded
    @Column
    private DateLog dateLog;

    public static Project fromDTO(ProjectDTO dto, TagDTO ... tags) {
        return new Project().setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setState(dto.getState())
                .setDeadLine(dto.getDeadLine())
                .setUiConfig(dto.getUiConfig())
                .setTrackers(new HashSet<>())
                .setTags(tags!=null?new HashSet<>(Arrays.stream(tags).map((x)->Tag.fromDTO(x,dto.getId().getUserId())).toList()):new HashSet<>());

    }

    public Project addTag(Tag... tags) {
        Arrays.asList(tags).addAll(this.tags);
        return this;
    }

    public Project quitTag(Tag... tags) {
        for (Tag tag : tags) this.tags.remove(tag);
        return this;
    }

}
