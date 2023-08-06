package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.entities.UserController;
import cat.itacademy.minddy.data.html.UserData;
import cat.itacademy.minddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/auth/user")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/data")
    public ResponseEntity<UserData> getUserData(@RequestParam(name = "today") LocalDate today, Authentication auth) {
        try {
            DefaultOidcUser user= (DefaultOidcUser) auth.getPrincipal();
            return ResponseEntity.ok(userService.getUserData(today, user.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
    }
    @Override
    @GetMapping("/ping")
    public String ping(@RequestParam String accessToken) {
        System.out.println("patatas");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                entity,
                User.class
        );

        User user = response.getBody();
        return user.getUsername();

    }

//    @Override
//    @PostMapping("/new")    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto, Authentication auth) {
//        try {
//            return ResponseEntity.ok(userService.createUser(dto, auth.getName()));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
//        }
//    }

    @Override
    public boolean deleteUser(boolean exportData,Authentication auth) {
            return userService.deleteUser(exportData,auth.getName());
    }
}
