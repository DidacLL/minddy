package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.PublicController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/public")
public class PublicControllerImpl implements PublicController {
    @Override
    @GetMapping("/ping")
    public ResponseEntity<String> ping(Authentication auth) {
        return ResponseEntity.ok(auth.getName());
    }
}
