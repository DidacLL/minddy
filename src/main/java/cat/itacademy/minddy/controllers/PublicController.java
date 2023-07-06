package cat.itacademy.minddy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/public")
public interface PublicController {
    ResponseEntity<String> ping(Authentication auth);
}
