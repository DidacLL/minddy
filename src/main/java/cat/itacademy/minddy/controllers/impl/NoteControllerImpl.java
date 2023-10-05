package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.NoteController;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.views.TaskData;
import cat.itacademy.minddy.services.NoteService;
import cat.itacademy.minddy.services.ProjectService;
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
@RequestMapping("v1/auth/note")
public class NoteControllerImpl implements NoteController {
    private static final String DEMO_ID = "DEMO_00FF";
    @Autowired
    TaskService taskService;
    @Autowired
    NoteService noteService;
    @Autowired
    TagService tagService;
    @Autowired
    ProjectService projectService;

    @Override
    @GetMapping("/data")
    public ResponseEntity<?> getFullNote(Authentication auth, @RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getFullNote(auth.getName(), UUID.fromString(id)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/data")
    public ResponseEntity<?> getFullNote(@RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getFullNote(DEMO_ID, UUID.fromString(id)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/task")
    public ResponseEntity<?> getTaskNotes(Authentication auth,@RequestParam String project,@RequestParam String id) {

        try {
            return ResponseEntity.ok(noteService.getTaskNotes(new HierarchicalId(auth.getName(),project),id));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/task")
    public ResponseEntity<?> getTaskNotes(@RequestParam String project,@RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getTaskNotes(new HierarchicalId(DEMO_ID,project),id));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/project")
    public ResponseEntity<?> getProjectNotes(Authentication auth, @RequestParam String id,@RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(noteService.getAllVisibleNotes(new HierarchicalId(auth.getName(),id),page,size));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }    }

    @Override
    @GetMapping("/demo/project")
    public ResponseEntity<?> getProjectNotes(@RequestParam String id,@RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(noteService.getAllVisibleNotes(new HierarchicalId(DEMO_ID,id),page,size));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    public ResponseEntity<?> updateNote(Authentication auth, @RequestParam NoteDTO note) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateNote(TaskData taskData) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteNote(Authentication auth, TaskData taskData) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteNote(TaskData taskData) {
        return null;
    }
}
