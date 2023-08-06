package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.DefaultTags;
import cat.itacademy.minddy.data.dao.User;
import cat.itacademy.minddy.data.dto.UserDTO;
import cat.itacademy.minddy.data.html.UserData;
import cat.itacademy.minddy.repositories.UserRepository;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.services.UserService;
import cat.itacademy.minddy.utils.MinddyException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    ProjectService projectService;
    @Autowired
    TagService tagService;

    @Override
    public UserData getUserData(LocalDate today, String userId) throws Exception {

        return UserData.fromEntity(repository.findById(userId).orElseThrow(() -> new Exception(userId + " does not exist")));
    }
    @Transactional
    public UserData registerNewUser(String userId, String userName, String uiConfig) throws Exception {
        if(existUser(userId))throw new MinddyException(418,"I'm a Teapot!");
        tagService.createTag(userId, DefaultTags.DELAYED.toDTO());
        tagService.createTag(userId,DefaultTags.FAVOURITE.toDTO());
        var rootTag= tagService.createTag(userId,DefaultTags.ROOT.toDTO());
        tagService.createTag(userId,DefaultTags.TASK_NOTE.toDTO());
        projectService.createRootProject(userId,"",userName,rootTag);
//        ArrayList<String> fav=new ArrayList<String>();
        createUser(new UserDTO(
                userId,
                userName,
//                null,
//                fav,
                ""
        ), userId);
        return getUserData(LocalDate.now(),userId);
    }

    private UserDTO createUser(UserDTO dto, String userId) throws MinddyException {
        if(!existUser(userId)) {
            return UserDTO.fromEntity(
                    repository.save(new User()
                    .setId(userId)
                    .setName(dto.getName())
                    .setUiConfig(dto.getUiConfig())
                    )
            );
        }


    throw new MinddyException(418,"USER EXISTS");
    }

    @Override
    public boolean deleteUser(boolean exportData, String userId) {
        repository.deleteById(userId);
        //todo cascade delete all elements?
        return !existUser(userId);
    }

    @Override
    public boolean existUser(String userId) {
        return repository.existsById(userId);
    }
}
