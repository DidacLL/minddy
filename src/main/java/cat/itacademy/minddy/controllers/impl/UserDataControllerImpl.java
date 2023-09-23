package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.UserDataController;
import cat.itacademy.minddy.data.html.ProjectStructure;
import cat.itacademy.minddy.data.html.TodayReport;
import cat.itacademy.minddy.data.html.requests.SearchRequest;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.services.UserService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static cat.itacademy.minddy.utils.MinddyException.getErrorResponse;
@CrossOrigin(value = "*")
@RestController
@RequestMapping("v1/auth/user")
public class UserDataControllerImpl implements UserDataController {
    private static final String DEMO_ID="DEMO_00FF";
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @GetMapping("/ping")
    public String ping(Authentication auth) {
        return auth.getName();
    }
    @GetMapping("/demo/ping")
    public String ping() {
        return "pong";
    }

    @Override
    @GetMapping("/data")
    public ResponseEntity<?> getUserData(Authentication auth, LocalDate today) {
        return getUserDataMain(today, auth.getName());
    }

    @Override
    @GetMapping("/demo/data")
    public ResponseEntity<?> getUserData(LocalDate today) {
        return getUserDataMain(today, DEMO_ID);
    }

    private ResponseEntity<?> getUserDataMain(LocalDate today, String name) {
        try {
            var userData = userService.getUserData(today, name);
            return ResponseEntity.ok(userData);
        } catch (MinddyException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getErrorMessage());
            errorResponse.put("code", String.valueOf(e.getErrorCode()));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @Override
    @GetMapping(value = "/structure")
    @Cacheable(value = "projectStructure", key = "#auth.name")
    public ResponseEntity<?> getUserProjects(Authentication auth, LocalDate today) {
        return getProjStructureMain(today, auth.getName());
    }

    @Override
    @GetMapping(value = "/demo/structure")
    @Cacheable(value = "projectStructure", key = "demo")
    public ResponseEntity<?> getUserProjects(LocalDate today) {
        return getProjStructureMain(today, DEMO_ID);
    }

    private ResponseEntity<?> getProjStructureMain(LocalDate today, String name) {
        try {
            ProjectStructure projectStructure = projectService.getProjectStructure(name, today);
            return ResponseEntity.ok(projectStructure);
        } catch (MinddyException e) {
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping(value = "/report")
    public ResponseEntity<?> getTodayReport(Authentication auth, LocalDate today, int elements) {
        return getTodayReportMain(today, elements,  auth.getName());
    }

    @Override
    @GetMapping(value = "/demo/report")

    public ResponseEntity<?> getTodayReport(LocalDate today, int elements) {
        return getTodayReportMain(today, elements, DEMO_ID);
    }

    private ResponseEntity<?> getTodayReportMain(LocalDate today, int elements, String name) {
        try {
            var report = new TodayReport(today,
                    taskService.getTodayTasks(name, today, elements, 0).stream().toList(),
                    taskService.getMissedTasks(name, today),
                    projectService.getNearToDeadLine(name, today)
            );
            return ResponseEntity.ok(report);
        } catch (MinddyException e) {
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }


    @Override
    @PostMapping(value = "/new")
    public ResponseEntity<?> registerNewUser(Authentication auth, String uiConfig) {
        String name = ((OAuth2User) auth.getPrincipal()).getName();
        try {
            return ResponseEntity.ok(userService.registerNewUser(auth.getName(), name, uiConfig));
        } catch (MinddyException e) {
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping(value = "/update")
    public ResponseEntity<?> updateUserConfig(Authentication auth, String uiConfig) {
            String name = auth.getName();
        return updateUserConfigMain(uiConfig, name);
    }

    @Override
    @PostMapping(value = "/demo/update")
    public ResponseEntity<?> updateUserConfig(String uiConfig) {
        return updateUserConfigMain(uiConfig, DEMO_ID);
    }

    private ResponseEntity<?> updateUserConfigMain(String uiConfig, String name) {
        try {
            return ResponseEntity.ok(userService.updateUser(name, uiConfig));
        } catch (MinddyException e) {
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    public ResponseEntity<?> search(Authentication auth, SearchRequest request) {
        return null;
    }

}
