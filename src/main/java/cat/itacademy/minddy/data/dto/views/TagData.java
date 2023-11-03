package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TagData {
    private String id;
    private boolean isHeritable;
    public static TagData fromEntity(Tag entity){
        return new TagData()
                .setId(entity.getId().getName())
                .setHeritable(entity.isHeritable());
    }
    public static TagData fromDTO(TagDTO dto){
        return new TagData()
                .setId(dto.getName())
                .setHeritable(dto.isHeritable());
    }
}
