package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dto.TagDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor
@Getter @Setter
@Table(name = "tags", indexes = {
        @Index(name = "idx_tag_by_visibility",columnList ="is_visible")
})
public class Tag {

    @EmbeddedId
    private TagId id;
    @Column(nullable = false,updatable = false,name = "is_visible")
    private boolean isVisible;
    @Column(nullable = false,updatable = false)
    private boolean isHeritable;
    @Embedded
    private DateLog dateLog;
//    @ManyToMany(mappedBy = "tags")
//    Set<Project> projects;
//    @ManyToMany(mappedBy = "tags")
//    Set<Note> notes;
//    @ManyToMany(mappedBy = "tags")
//    Set<Task> tasks;
    public static Tag fromDTO(TagDTO dto){
        return new Tag().setVisible(dto.isVisible())
                .setHeritable(dto.isHeritable())
                .setId(new TagId().setName(dto.getName()));
    }
    public static Tag fromDTO(TagDTO dto, String userId){
        return new Tag().setVisible(dto.isVisible())
                .setHeritable(dto.isHeritable())
                .setId(new TagId(userId, dto.getName()));
    }

}
