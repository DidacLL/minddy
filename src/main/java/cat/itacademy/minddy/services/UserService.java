package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.html.UserData;
import cat.itacademy.minddy.utils.MinddyException;

import java.time.LocalDate;

public interface UserService {
    UserData getUserData(LocalDate today, String userId) throws MinddyException;
    UserData registerNewUser(String userId, String userName, String uiConfig) throws MinddyException;

    boolean deleteUser(boolean exportData, String userId);
    boolean existUser(String userId);

    UserData updateUser(String userId, String uiConfig) throws MinddyException;

}
