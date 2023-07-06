package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.entities.UserController;
import cat.itacademy.minddy.data.dto.UserDTO;
import cat.itacademy.minddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/v1/user")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/data")
    public ResponseEntity<UserDTO> getUserData(@RequestParam(name = "today") Date today, Authentication auth) {
        try {
            return ResponseEntity.ok(userService.getUserData(today, auth.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }

    @Override
    @PostMapping("/new")    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto, Authentication auth) {
        try {
            return ResponseEntity.ok(userService.createUser(dto, auth.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }

    @Override
    public boolean deleteUser(boolean exportData,Authentication auth) {
            return userService.deleteUser(exportData,auth.getName());
    }
}
