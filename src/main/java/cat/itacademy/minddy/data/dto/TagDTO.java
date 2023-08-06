package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.dao.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TagDTO {
    private String name;
    private boolean isVisible,isHeritable;
    public static TagDTO fromEntity(Tag entity){
        return new TagDTO()
                .setName(entity.getId().getName())
                .setHeritable(entity.isHeritable())
                .setVisible(entity.isVisible());
    }
}
