package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.dto.views.NoteRequest;
import cat.itacademy.minddy.data.dto.views.TaskData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/pin")
    ResponseEntity<?> getProjectPinnedNotes(Authentication auth, @RequestParam String id);

    @GetMapping("/demo/pin")
    ResponseEntity<?> getProjectPinnedNotes(@RequestParam String id);

    @GetMapping("/tag")
    ResponseEntity<?> getNoteTags(Authentication auth, @RequestParam String id);

    @GetMapping("/demo/tag")
    ResponseEntity<?> getNoteTags(@RequestParam String id);

    @PostMapping("/update")
    ResponseEntity<?> updateNote(Authentication auth, @RequestBody NoteRequest noteRequest);

    @PostMapping("/demo/update")
    ResponseEntity<?> updateNote(@RequestBody NoteRequest noteRequest);

    @PostMapping("/new")
    ResponseEntity<?> createNewNote(Authentication auth, @RequestBody NoteRequest noteRequest);

    @PostMapping("/demo/new")
    ResponseEntity<?> createNewNote(@RequestBody NoteRequest noteRequest);

    ResponseEntity<?> deleteNote(Authentication auth, TaskData taskData);
    ResponseEntity<?> deleteNote(TaskData taskData);
}
