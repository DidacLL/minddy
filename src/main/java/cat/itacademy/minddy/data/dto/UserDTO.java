package cat.itacademy.minddy.data.dto;

import cat.itacademy.minddy.data.dao.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UserDTO {
    private String id;
    private String name;
    private String rootProject;
    List<String> favourites;
    String uiConfig;


    static UserDTO fromEntity(User entity){
        return new UserDTO().setId(entity.getId())
                .setName(entity.getName())
                .setRootProject(entity.getRootProject().getId().getOwnId())
                .setFavourites(entity.getFavourites())
                .setUiConfig(entity.getUiConfig());
    }
}
