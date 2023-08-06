package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.TestController;
import cat.itacademy.minddy.data.html.ProjectStructure;
import cat.itacademy.minddy.data.html.TodayReport;
import cat.itacademy.minddy.data.html.UserData;
import cat.itacademy.minddy.repositories.UserRepository;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.services.UserService;
import org.apache.hc.core5.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/auth/test")
public class TestControllerImpl implements TestController {
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserRepository userRepository;

    @Override
    @GetMapping("/ping")
    public ResponseEntity<String> ping(String ping) {
        try {
            return ResponseEntity.ok(ping.replaceAll("i", "o"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Missing required parameter 'ping'");
        }
    }

    @Override
    @GetMapping("/data")
    public ResponseEntity<UserData> getUserData( String userId,  String date) {
        try {
            return ResponseEntity.ok(userService.getUserData(LocalDate.parse(date), userId));
        } catch (Exception e) {
            //fixme Not tested message in header
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("error",e.getMessage()).body(null);
        }
    }

    @Override
    @GetMapping("/id")
    public String getUserId(@RequestParam(name = "username") String username) throws HttpException {
        return userRepository.findByNameIgnoreCase(username).getId();
    }

    @Override
    @GetMapping("/register")
    public ResponseEntity<UserData> registerNewUser(String userId, String userName, String uiConfig)  {
        try {
            return ResponseEntity.ok(userService.registerNewUser(userId, userName, uiConfig));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("error",e.getMessage()).body(null);
        }
    }

    @Override
    public ResponseEntity<ProjectStructure> getProjectStructure(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<TodayReport> getTodayReport(String userId, LocalDate date) {
        return null;
    }
}
