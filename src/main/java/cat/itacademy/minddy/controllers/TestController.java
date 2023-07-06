package cat.itacademy.minddy.controllers;

import org.springframework.http.ResponseEntity;

public interface TestController {

    public ResponseEntity<String[]> authenticatedPing();
}
