package cat.itacademy.minddy.controllers.entities;

import cat.itacademy.minddy.data.html.UserData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public interface UserController {

    ResponseEntity<UserData> getUserData(LocalDate today, Authentication auth);

    String ping(@RequestParam String token);

//    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto, Authentication auth);
    boolean deleteUser(@RequestParam boolean exportData,Authentication auth);



}
