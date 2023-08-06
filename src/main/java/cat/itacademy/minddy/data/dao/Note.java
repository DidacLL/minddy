package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dto.NoteDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_note_by_user_visibility", columnList = "user,is_visible")
})
@NoArgsConstructor
@Getter
@Setter
public class Note {
    static String numRegx = "^[-+]?\\d+(\\.\\d+)?$";
    static String urlRegx = "^(?:(?:https?|ftp)://)?(?:www\\.|[a-zA-Z]\\.)?[\\w-]+\\.[\\w.-]+(/[\\w-./?%&=]*)?$";
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    //-------------------------READABLE STUFF
    @Column(nullable = false)
    private String body;
    @Column(name = "name", columnDefinition = "VARCHAR(30)")
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
    @JoinColumns({
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
                .setTags(dto.getTags()!=null?dto.getTags().stream().map(Tag::fromDTO).collect(Collectors.toList()):new ArrayList<>())
                .setVisible(dto.isVisible());
    }

    public Note addTag(Tag tag) {
        this.tags.add(tag);
        return this;
    }
}
