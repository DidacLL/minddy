package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteDTO {
    private UUID id;
    private String name="";
    private String body="";
    private NoteType type;
    private boolean isVisible;

    public static NoteDTO fromEntity(Note entity){
        return new NoteDTO().setId(entity.getId())
                .setName(entity.getName())
                .setBody(entity.getBody())
                .setType(entity.getType())
                .setVisible(entity.isVisible());
    }

}
