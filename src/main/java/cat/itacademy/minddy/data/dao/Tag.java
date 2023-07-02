package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dto.TagDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity @NoArgsConstructor
@Getter @Setter
@Table(name = "tags", indexes = {
        @Index(name = "idx_tags_by_user",columnList ="user_id")
})
public class Tag {


    private boolean isVisible,isHeritable;
    @EmbeddedId
    private TagId id;
    @Embedded
    private DateLog dateLog;

    public static Tag fromDTO(TagDTO dto){
        return new Tag().setVisible(dto.isVisible())
                .setHeritable(dto.isHeritable())
                .setId(new TagId().setName(dto.getName()));
    }
    public static Tag fromDTO(TagDTO dto, UUID userId){
        return new Tag().setVisible(dto.isVisible())
                .setHeritable(dto.isHeritable())
                .setId(new TagId(userId, dto.getName()));
    }

}
