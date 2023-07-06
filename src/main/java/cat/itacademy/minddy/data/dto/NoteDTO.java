package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dao.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter @Setter
public class NoteDTO {
    static String numRegx = "^[-+]?\\d+(\\.\\d+)?$";
    static String urlRegx = "^(?:(?:https?|ftp)://)?(?:www\\.|[a-zA-Z]\\.)?[\\w-]+\\.[\\w.-]+(/[\\w-./?%&=]*)?$";
    private UUID id;
    private String content;
    private NoteType type;
    private boolean isVisible;
    private List<Tag> tags;

}
