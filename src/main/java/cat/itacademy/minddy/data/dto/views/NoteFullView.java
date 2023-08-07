package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class NoteFullView {
    NoteDTO noteDTO;
    List<TagDTO> tags;
}
