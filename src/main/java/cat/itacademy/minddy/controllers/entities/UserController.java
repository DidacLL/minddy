package cat.itacademy.minddy.controllers.entities;

import cat.itacademy.minddy.data.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public interface UserController {

    ResponseEntity<UserDTO> getUserData(Date today, Authentication auth);

    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto,Authentication auth);
    public boolean deleteUser(@RequestParam boolean exportData,Authentication auth);



}
