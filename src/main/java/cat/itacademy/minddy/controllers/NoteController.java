package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.views.TaskData;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("v1/auth/note")

public interface NoteController {


    @GetMapping("/data")
    ResponseEntity<?> getFullNote(Authentication auth,String id);

    @GetMapping("/demo/data")
    ResponseEntity<?> getFullNote(String id);
    @GetMapping("/task")

    ResponseEntity<?> getTaskNotes(Authentication auth, String project,String id);
    @GetMapping("/demo/task")

    ResponseEntity<?> getTaskNotes(String project,String id);
    @GetMapping("/project")

    ResponseEntity<?> getProjectNotes(Authentication auth,String id, int page, int size);
    @GetMapping("/demo/project")


    ResponseEntity<?> getProjectNotes(String id, int page, int size);

    ResponseEntity<?> updateNote(Authentication auth, NoteDTO note);

    ResponseEntity<?> updateNote(TaskData taskData);
    ResponseEntity<?> deleteNote(Authentication auth,TaskData taskData);
    ResponseEntity<?> deleteNote(TaskData taskData);
}
