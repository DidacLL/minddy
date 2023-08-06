package cat.itacademy.minddy.data.html;

import cat.itacademy.minddy.data.dao.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserData {
    private String userName;
//FIXME(Must filter by _FAV_ tag)    private String[] favourites;
    private String uiConfig;

    public static UserData fromEntity(User user) {
        return new UserData(user.getName(),  user.getUiConfig());
    }
}
