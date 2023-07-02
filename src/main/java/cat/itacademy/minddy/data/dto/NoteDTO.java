package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static cat.itacademy.minddy.data.config.NoteType.*;


@Entity @Table(name = "notes")
@NoArgsConstructor
@Getter
public class NoteDTO {
    static String numRegx = "^[-+]?\\d+(\\.\\d+)?$";
    static String urlRegx = "^(?:(?:https?|ftp)://)?(?:www\\.|[a-zA-Z]\\.)?[\\w-]+\\.[\\w.-]+(/[\\w-./?%&=]*)?$";

    @EmbeddedId
    private HierarchicalId id;
    //-------------------------READABLE STUFF
    private String content;
    @Enumerated
    private NoteType type;
    private boolean isVisible;
    @Embedded
    private DateLog dateLog;
    @ManyToMany
    private List<Tag> tags;

    public NoteDTO setId(HierarchicalId id) {
        this.id = id;
        return this;
    }

    public NoteDTO setContent(String content) {
        content = content.trim();
        if (type != null) {
            switch (type) {
                case NUMBER -> {
                    if (Double.isNaN(Double.parseDouble(content))) {
                        this.type = TEXT;
                    }
                }
                case TEXT -> {
                }
                case LINK -> {
                    if (!content.startsWith("http")) content = "https://" + content;
                }
                case SEARCH -> content="_#"+content;
                default -> {
                    break; // TODO: 02/07/2023 MANAGE EXCEPTION
                }
            }
            setContent(content);
            this.content = content;
            return this;
        }
        if(content.matches(numRegx))this.type= NUMBER;
        else if(content.matches(urlRegx))this.type= LINK;
        else if(content.startsWith("_#"))this.type= SEARCH;
        else this.type=TEXT;

        return this;
    }

    public NoteDTO setType(NoteType type) {
        this.type = type;
        return this;
    }

    public NoteDTO setVisible(boolean visible) {
        isVisible = visible;
        return this;
    }

    public NoteDTO setDateLog(DateLog dateLog) {
        this.dateLog = dateLog;
        return this;
    }

    public NoteDTO setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }
}
