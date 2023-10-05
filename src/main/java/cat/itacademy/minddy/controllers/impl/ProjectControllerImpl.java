package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.ProjectController;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.ProjectData;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static cat.itacademy.minddy.utils.MinddyException.getErrorResponse;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("v1/auth/project")
public class ProjectControllerImpl implements ProjectController {
    private static final String DEMO_ID = "DEMO_00FF";
    @Autowired
    TaskService taskService;
    @Autowired
    TagService tagService;
    @Autowired
    ProjectService projectService;

    @Override
    @PostMapping("/data")
    public ResponseEntity<?> getFullProject(Authentication auth, @RequestParam String id) {
        try {
            return ResponseEntity.ok(ProjectData.fromDTO(projectService.getProject(new HierarchicalId(auth.getName(), id))));
        } catch (MinddyException e) {
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/data")
    public ResponseEntity<?> getFullProject(@RequestParam String id) {
        try {
            return ResponseEntity.ok(ProjectData.fromDTO(projectService.getProject(new HierarchicalId(DEMO_ID, id))));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/todo")

    public ResponseEntity<?> getProjectPriorityTasks(Authentication auth, @RequestParam String id, @RequestParam int size, @RequestParam int page) {
        try {
            return ResponseEntity.ok(taskService.getProjectPendingMinTasks(new HierarchicalId(auth.getName(), id), size, page));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/todo")
    public ResponseEntity<?> getProjectPriorityTasks(@RequestParam String id, @RequestParam int size, @RequestParam int page) {
        try {
            return ResponseEntity.ok(taskService.getProjectPendingMinTasks(new HierarchicalId(DEMO_ID, id), size, page));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/tag")

    public ResponseEntity<?> getProjectTags(Authentication auth, @RequestParam String id) {
        try {

            return ResponseEntity.ok(tagService.getProjectTags(new HierarchicalId(auth.getName(), id), true));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e);
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    @GetMapping("/demo/tag")

    public ResponseEntity<?> getProjectTags(@RequestParam String id) {
            try {

        return ResponseEntity.ok(tagService.getProjectTags(new HierarchicalId(DEMO_ID, id), true));

            } catch (Exception e) {
                System.out.println("ERRRRR! " + e);
                return ResponseEntity.badRequest().body(e);
            }
    }

    @Override
    @GetMapping("/tasks")
    public ResponseEntity<?> getProjectTasks(Authentication auth, @RequestParam String id, @RequestParam int size, @RequestParam int page, @RequestParam(name = "viewall") boolean viewAll, @RequestParam boolean subproject) {
        try {
            var notIn = new TaskState[]{TaskState.DONE, TaskState.DISCARDED};
            if (subproject)
                return ResponseEntity.ok(taskService.getProjectExpandedTasks(new HierarchicalId(auth.getName(), id), size, page, viewAll ? null : notIn));
            return ResponseEntity.ok(taskService.getProjectExpandedTasksExclusive(new HierarchicalId(auth.getName(), id), size, page, viewAll ? null : notIn));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/tasks")
    public ResponseEntity<?> getProjectTasks(@RequestParam String id, @RequestParam int size, @RequestParam int page, @RequestParam(name = "viewall") boolean viewAll, @RequestParam boolean subproject) {
        try {
            var notIn = new TaskState[]{TaskState.DONE, TaskState.DISCARDED};
            if (subproject)
                return ResponseEntity.ok(taskService.getProjectExpandedTasks(new HierarchicalId(DEMO_ID, id), size, page, viewAll ? null : notIn));
            return ResponseEntity.ok(taskService.getProjectExpandedTasksExclusive(new HierarchicalId(DEMO_ID, id), size, page, viewAll ? null : notIn));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<?> updateProject(Authentication auth, @RequestBody ProjectData projectData) {
        try {
            return ResponseEntity.ok(projectService.updateProject(ProjectDTO.fromData(auth.getName(), projectData)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PutMapping("/demo/update")
    public ResponseEntity<?> updateProject(@RequestBody ProjectData projectData) {
        try {
            return ResponseEntity.ok(projectService.updateProject(ProjectDTO.fromData(DEMO_ID, projectData)));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

    @Override
    @PutMapping("/new")
    public ResponseEntity<?> createNewProject(Authentication auth, @RequestBody ProjectData projectData, @RequestBody TagDTO... tags) {
        try {
            return ResponseEntity.ok(projectService.createProject(ProjectDTO.fromData(auth.getName(), projectData), tags));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

    @Override
    @PutMapping("/demo/new")
    public ResponseEntity<?> createNewProject(@RequestBody ProjectData projectData, @RequestBody TagDTO... tags) {
        try {
            return ResponseEntity.ok(projectService.createProject(ProjectDTO.fromData(DEMO_ID, projectData), tags));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

}
