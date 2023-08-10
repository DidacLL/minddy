package cat.itacademy.minddy.data.dao;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "VARCHAR(36)")
    private String  id;
    private String name;
    private String uiConfig;
    @Embedded
    private DateLog dateLog;

    static User fromDTO(UserDTO dto){
        return new User().setId(dto.getId())
                .setName(dto.getName())
//                .setRootProject(new Project().setId(new HierarchicalId().setOwnId(dto.getRootProject()))) //FIXME
//                .setFavourites(dto.getFavourites())
                .setUiConfig(dto.getUiConfig());
    }
}
