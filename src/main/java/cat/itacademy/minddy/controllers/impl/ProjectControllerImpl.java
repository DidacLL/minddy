package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.ProjectController;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.ProjectData;
import cat.itacademy.minddy.data.dto.views.ProjectRequest;
import cat.itacademy.minddy.data.dto.views.TagData;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

            return ResponseEntity.ok(tagService.getProjectTags(new HierarchicalId(auth.getName(), id), true).stream().map(TagData::fromDTO));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e);
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    @GetMapping("/demo/tag")

    public ResponseEntity<?> getProjectTags(@RequestParam String id) {
        try {

            return ResponseEntity.ok(tagService.getProjectTags(new HierarchicalId(DEMO_ID, id), true).stream().map(TagData::fromDTO));

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
    @PostMapping("/update")
    public ResponseEntity<?> updateProject(Authentication auth, @RequestBody ProjectRequest projectRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : projectRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }
            return ResponseEntity.ok(ProjectData.fromDTO(projectService.updateProject(ProjectDTO.fromData(auth.getName(), projectRequest.getProjectData()),
                    tags.toArray(new TagDTO[0])  )));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping("/demo/update")
    public ResponseEntity<?> updateProject(@RequestBody ProjectRequest projectRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : projectRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }
            return ResponseEntity.ok(ProjectData.fromDTO(projectService.updateProject(ProjectDTO.fromData(DEMO_ID, projectRequest.getProjectData()), tags.toArray(new TagDTO[0]))));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> createNewProject(Authentication auth, @RequestBody ProjectRequest projectRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : projectRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }


            return ResponseEntity.ok
                    (projectService.createProject(ProjectDTO.fromData(auth.getName(),
                                    projectRequest.getProjectData()),
                            tags.toArray(new TagDTO[0]))
                    );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

    @Override
    @PostMapping("/demo/new")
    public ResponseEntity<?> createNewProject(@RequestBody ProjectRequest projectRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : projectRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }

            return ResponseEntity.ok(projectService.createProject(ProjectDTO.fromData(DEMO_ID,
                            projectRequest.getProjectData()),
                    tags.toArray(new TagDTO[0])));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }

    }

}
