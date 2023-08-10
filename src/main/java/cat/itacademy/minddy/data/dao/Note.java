package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.interfaces.Taggable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_note_by_user_visibility", columnList = "user,is_visible")
})
@NoArgsConstructor
@Getter
@Setter
public class Note implements Taggable<Note> {
    static String numRegx = "^[-+]?\\d+(\\.\\d+)?$";
    static String urlRegx = "^(?:(?:https?|ftp)://)?(?:www\\.|[a-zA-Z]\\.)?[\\w-]+\\.[\\w.-]+(/[\\w-./?%&=]*)?$";
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    //-------------------------READABLE STUFF
    @Column(nullable = false)
    private String body;
    @Column(name = "name", columnDefinition = "VARCHAR(36)")
    private String name;
    @Enumerated
    @Column(nullable = false)
    private NoteType type;
    @Column(name = "is_visible", nullable = false, updatable = false)
    private boolean isVisible;
    @Embedded
    private DateLog dateLog;
    @ManyToMany
    private List<Tag> tags;
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "user", referencedColumnName = "user_id", nullable = false),
            @JoinColumn(name = "parent_id", referencedColumnName = "holder_id", nullable = false),
            @JoinColumn(name = "holder_id", referencedColumnName = "own_id", nullable = false)
    })
    private Project holder;

    public static Note fromDTO(NoteDTO dto) {

        return new Note()
                .setName(dto.getName())
                .setBody(dto.getBody())
                .setType(dto.getType())
                .setVisible(dto.isVisible());
    }

    public Note addTag(Tag... tags) {
        for(Tag tag : tags)if(!this.tags.contains(tag))this.tags.add(tag);
        return this;
    }
    public Note quitTag(Tag ... tags) {
        for (Tag tag : tags) this.tags.remove(tag);
        return this;
    }
}
