package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Note;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.NoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class NoteRequest {
    NoteDTO noteDTO;
    String holderId;
    String[] tags;


    public static NoteRequest fromDTO(String holderId,NoteDTO dto, String...tags){
        return new NoteRequest(dto,holderId,tags);
    }
    public static NoteRequest fromEntity(Note entity){
        return new NoteRequest(NoteDTO.fromEntity(entity),
                entity.getHolder().getId().toString(),
                entity.getTags().stream().map(Tag::toString).toArray(String[]::new));

    }

    public static NoteRequest parseJSON(String data){
        var mapper= new ObjectMapper();

        try {
            var val= mapper.readTree(data);
            return mapper.treeToValue(val, NoteRequest.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
