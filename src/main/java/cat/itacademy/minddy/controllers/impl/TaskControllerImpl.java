package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.TaskController;
import cat.itacademy.minddy.data.dto.views.TaskData;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import static cat.itacademy.minddy.utils.MinddyException.getErrorResponse;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("v1/auth/task")
public class TaskControllerImpl implements TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    TagService tagService;
    private static final String DEMO_ID = "DEMO_00FF";
    @Override
    @GetMapping("/data")
    public ResponseEntity<?> getFullTask(Authentication auth,@RequestParam String id) {
        try {
            return ResponseEntity.ok(TaskData.fromDTO(taskService.getTask(auth.getName(), UUID.fromString(id))));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/data")
    public ResponseEntity<?> getFullTask(@RequestParam String id) {
        try {
            return ResponseEntity.ok(taskService.getExpandedTask(DEMO_ID, UUID.fromString(id)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/data/tags")
    public ResponseEntity<?> getTaskTags(Authentication auth, @RequestParam String id) {
        try {
            return ResponseEntity.ok(tagService.getTaskTags(auth.getName(), UUID.fromString(id),true));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/demo/data/tags")
    public ResponseEntity<?> getTaskTags(@RequestParam String id) {
        try {
            return ResponseEntity.ok(tagService.getTaskTags(DEMO_ID, UUID.fromString(id),true));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateTask(Authentication auth, TaskData taskData) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateTask(TaskData taskData) {
        return null;
    }
}
