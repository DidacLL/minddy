package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.UserDTO;
import cat.itacademy.minddy.utils.converters.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String  id;
    private String name;
    @OneToOne
    private Project rootProject;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "JSON")
    private List<String> favourites;
    private String uiConfig;
    @Embedded
    private DateLog dateLog;

    static User fromDTO(UserDTO dto){
        return new User().setId(dto.getId())
                .setName(dto.getName())
                .setRootProject(new Project().setId(new HierarchicalId().setOwnId(dto.getRootProject()))) //FIXME
                .setFavourites(dto.getFavourites())
                .setUiConfig(dto.getUiConfig());
    }
}
