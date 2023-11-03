package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.NoteController;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.NoteRequest;
import cat.itacademy.minddy.data.dto.views.TagData;
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

import java.util.ArrayList;
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
    public ResponseEntity<?> getTaskNotes(Authentication auth, @RequestParam String project, @RequestParam String id) {

        try {
            return ResponseEntity.ok(noteService.getTaskNotes(new HierarchicalId(auth.getName(), project), id));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/task")
    public ResponseEntity<?> getTaskNotes(@RequestParam String project, @RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getTaskNotes(new HierarchicalId(DEMO_ID, project), id));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/project")
    public ResponseEntity<?> getProjectNotes(Authentication auth, @RequestParam String id, @RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(noteService.getAllVisibleNotes(new HierarchicalId(auth.getName(), id), page, size));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/project")
    public ResponseEntity<?> getProjectNotes(@RequestParam String id, @RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(noteService.getAllVisibleNotes(new HierarchicalId(DEMO_ID, id), page, size));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/pin")
    public ResponseEntity<?> getProjectPinnedNotes(Authentication auth, @RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getProjectPinnedNotes(new HierarchicalId(auth.getName(), id)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }


    @Override
    @GetMapping("/demo/pin")
    public ResponseEntity<?> getProjectPinnedNotes(@RequestParam String id) {
        try {
            return ResponseEntity.ok(noteService.getProjectPinnedNotes(new HierarchicalId(DEMO_ID, id)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/tag")
    public ResponseEntity<?> getNoteTags(Authentication auth, @RequestParam String id) {
        try {

            return ResponseEntity.ok(tagService.getNoteTags(auth.getName(), UUID.fromString(id), true).stream().map(TagData::fromDTO));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e);
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    @GetMapping("/demo/tag")

    public ResponseEntity<?> getNoteTags(@RequestParam String id) {
        try {

            return ResponseEntity.ok(tagService.getNoteTags(DEMO_ID, UUID.fromString(id), true).stream().map(TagData::fromDTO));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e);
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<?> updateNote(Authentication auth, @RequestBody NoteRequest noteRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = noteRequest.getTags();
            if (val != null && val.length > 0) for (String name  : noteRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }
            var holder = noteRequest.getHolderId();
            return ResponseEntity.ok(noteService.updateNote(
                    new HierarchicalId(auth.getName(), holder),
                    noteRequest.getNoteDTO(),
                    tags.toArray(new TagDTO[0]))
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping("/demo/update")
    public ResponseEntity<?> updateNote(@RequestBody NoteRequest noteRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = noteRequest.getTags();
            if (val != null && val.length > 0) for (String name  : noteRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }
            var holder = noteRequest.getHolderId();
            return ResponseEntity.ok(noteService.updateNote(
                    new HierarchicalId(DEMO_ID, holder),
                    noteRequest.getNoteDTO(),
                    tags.toArray(new TagDTO[0]))
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> createNewNote(Authentication auth, @RequestBody NoteRequest noteRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = noteRequest.getTags();
            if (val != null && val.length> 0) for (String name : noteRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }
            var holder = noteRequest.getHolderId();
            return ResponseEntity.ok(
                    noteService.createNewNote(
                            new HierarchicalId(auth.getName(),
                                    holder),
                            noteRequest.getNoteDTO(),
                            tags.toArray(new TagDTO[0]))

            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }


    }

    @Override
    @PostMapping("/demo/new")
    public ResponseEntity<?> createNewNote(@RequestBody NoteRequest noteRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = noteRequest.getTags();
            if (val != null && val.length > 0) for (String name : noteRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }
            var holder = noteRequest.getHolderId();
            return ResponseEntity.ok(
                    noteService.createNewNote(
                            new HierarchicalId(DEMO_ID,
                                    holder),
                            noteRequest.getNoteDTO(),
                            tags.toArray(new TagDTO[0]))

            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

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
