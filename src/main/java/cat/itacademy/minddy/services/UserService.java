package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.html.UserData;
import org.apache.hc.core5.http.HttpException;

import java.time.LocalDate;

public interface UserService {
    UserData getUserData(LocalDate today, String userId) throws HttpException, Exception;
    public UserData registerNewUser(String userId, String userName, String uiConfig) throws HttpException, Exception;

    boolean deleteUser(boolean exportData, String userId);
    boolean existUser(String userId);
}
