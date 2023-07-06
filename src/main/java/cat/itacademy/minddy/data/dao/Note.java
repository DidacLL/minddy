package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.NoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@Entity @Table(name = "notes")
@NoArgsConstructor
@Getter @Setter
public class Note {
    static String numRegx = "^[-+]?\\d+(\\.\\d+)?$";
    static String urlRegx = "^(?:(?:https?|ftp)://)?(?:www\\.|[a-zA-Z]\\.)?[\\w-]+\\.[\\w.-]+(/[\\w-./?%&=]*)?$";
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;
    //-------------------------READABLE STUFF
    private String content;
    @Enumerated
    private NoteType type;
    private boolean isVisible;
    @Embedded
    private DateLog dateLog;
    @ManyToMany
    private List<Tag> tags;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "user_id"),
            @JoinColumn(name = "parent_id", referencedColumnName = "holder_id"),
            @JoinColumn(name = "holder_id", referencedColumnName = "own_id")
    })
    private Project holder;

}
