package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.MainController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class MainControllerImpl implements MainController {
    @Override
    @GetMapping("/demo/ping")
    public String ping() {
        return "WELCOME!";
    }
}
