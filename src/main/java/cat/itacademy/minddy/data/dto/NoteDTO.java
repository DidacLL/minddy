package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NoteDTO {
    private UUID id;
    private String name;
    private String body;
    private NoteType type;
    private List<TagDTO> tags;
    private boolean isVisible;

    public static NoteDTO fromEntity(Note entity){
        return new NoteDTO().setId(entity.getId())
                .setName(entity.getName())
                .setBody(entity.getBody())
                .setType(entity.getType())
                .setTags(entity.getTags().stream().map(TagDTO::fromEntity).collect(Collectors.toList()))
                .setVisible(entity.isVisible());
    }

}
