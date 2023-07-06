package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.dto.UserDTO;
import org.apache.hc.core5.http.HttpException;

import java.util.Date;

public interface UserService {
    UserDTO getUserData(Date today, String userId);

    UserDTO createUser(UserDTO dto, String userId) throws HttpException;

    boolean deleteUser(boolean exportData, String userId);
    boolean existUser(String userId);
}
