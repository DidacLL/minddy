package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteMinimal {
    private UUID id;

    public static NoteMinimal fromEntity(Note entity){
        return new NoteMinimal().setId(entity.getId());
    }

}
