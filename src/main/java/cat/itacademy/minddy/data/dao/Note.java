package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static cat.itacademy.minddy.data.config.NoteType.*;


@Entity @Table(name = "notes")
@NoArgsConstructor
@Getter
public class Note {
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

    public void setId(HierarchicalId id) {
        this.id = id;
    }

    public Note setContent(String content) {
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

        return null;
    }

    public void setType(NoteType type) {
        this.type = type;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setDateLog(DateLog dateLog) {
        this.dateLog = dateLog;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
