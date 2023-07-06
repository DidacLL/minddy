package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.User;
import cat.itacademy.minddy.data.dto.UserDTO;
import cat.itacademy.minddy.repositories.UserRepository;
import cat.itacademy.minddy.services.UserService;
import org.apache.hc.core5.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;
    @Override
    public UserDTO getUserData(Date today, String userId) {
        // TODO: 05/07/2023 define needed user data
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO dto, String userId) throws HttpException {
        if(!existUser(userId))return UserDTO.fromEntity(
                repository.save(new User()
                .setId(userId)
                .setName(dto.getName())
                .setUiConfig(dto.getUiConfig())
                .setFavourites(dto.getFavourites())
                .setRootProject(new Project())
                        //fixme root project must be created from project service? CYCLE: USER --> PROJECT?
                )
        );
    throw new HttpException("USER EXISTS");
    }

    @Override
    public boolean deleteUser(boolean exportData, String userId) {
        return false;
    }

    @Override
    public boolean existUser(String userId) {
        return false;
    }
}
