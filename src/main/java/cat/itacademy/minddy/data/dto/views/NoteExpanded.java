package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class NoteExpanded {
    private UUID id;
    private String name;
    private String body;
    private NoteType type;
    private boolean isVisible;

    public NoteExpanded(UUID id, String name, String body, int type, boolean isVisible) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.type = NoteType.values()[type];
        this.isVisible = isVisible;
    }

    public static NoteExpanded fromEntity(Note entity){
        return new NoteExpanded().setId(entity.getId())
                .setName(entity.getName())
                .setBody(entity.getBody())
                .setType(entity.getType())
                .setVisible(entity.isVisible());
    }

}
