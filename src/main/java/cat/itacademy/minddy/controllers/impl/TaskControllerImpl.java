package cat.itacademy.minddy.controllers.impl;

import cat.itacademy.minddy.controllers.TaskController;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TagData;
import cat.itacademy.minddy.data.dto.views.TaskData;
import cat.itacademy.minddy.data.dto.views.TaskRequest;
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
@RequestMapping("v1/auth/task")
public class TaskControllerImpl implements TaskController {
    private static final String DEMO_ID = "DEMO_00FF";
    @Autowired
    TaskService taskService;
    @Autowired
    TagService tagService;
    @Autowired
    ProjectService projectService;

    @Override
    @GetMapping("/data")
    public ResponseEntity<?> getFullTask(Authentication auth, @RequestParam String id) {
        try {
            String user = auth.getName();
            return ResponseEntity.ok(TaskData.fromDTO(taskService.getTaskHolder(user, id), taskService.getTask(user, UUID.fromString(id))));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/demo/data")
    public ResponseEntity<?> getFullTask(@RequestParam String id) {
        try {
            return ResponseEntity.ok(TaskData.fromDTO(taskService.getTaskHolder(DEMO_ID, id),
                    taskService.getTask(DEMO_ID, UUID.fromString(id))));
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @GetMapping("/data/tags")
    public ResponseEntity<?> getTaskTags(Authentication auth, @RequestParam String id) {
        try {
            return ResponseEntity.ok(tagService.getTaskTags(auth.getName(), UUID.fromString(id), true).stream().map(TagData::fromDTO));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/demo/data/tags")
    public ResponseEntity<?> getTaskTags(@RequestParam String id) {
        try {
            return ResponseEntity.ok(tagService.getTaskTags(DEMO_ID, UUID.fromString(id), true).stream().map(TagData::fromDTO));
        } catch (Exception e) {
            System.out.println("ERRRRR! " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<?> updateTask(Authentication auth, @RequestBody TaskRequest taskRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = taskRequest.getTags();
            if (val!=null && val.length>0)for (String name : taskRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }
            var holder =taskRequest.getTaskData().getHolder();
            return ResponseEntity.ok(
                    TaskData.fromDTO(holder,
                            taskService.updateTask(
                                    new HierarchicalId(auth.getName(),
                                            holder),
                                    TaskDTO.fromData(taskRequest.getTaskData()),
                                    tags.toArray(new TagDTO[0]))
                    )
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping("/demo/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskRequest taskRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            var val = taskRequest.getTags();
            if (val!=null && val.length>0)for (String name : taskRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }
            var holder =taskRequest.getTaskData().getHolder();
            return ResponseEntity.ok(
                    TaskData.fromDTO(holder,
                            taskService.updateTask(
                                    new HierarchicalId(DEMO_ID,
                                            holder),
                                    TaskDTO.fromData(taskRequest.getTaskData()),
                                    tags.toArray(new TagDTO[0]))
                    )
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> createNewTask(Authentication auth, @RequestBody TaskRequest taskRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : taskRequest.getTags()) {
                tags.add(tagService.getTag(auth.getName(), name));
            }
            return ResponseEntity.ok(
                    TaskData.fromDTO(
                            taskRequest.getTaskData().getHolder(),
                            taskService.createNewTask(
                                    projectService.getProjectEntity(
                                            new HierarchicalId(auth.getName(),
                                                    taskRequest.getTaskData().getHolder())),
                                    TaskDTO.fromData(taskRequest.getTaskData()),
                                    tags.toArray(new TagDTO[0]))
                    )
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }


    }

    @Override
    @PostMapping("/demo/new")
    public ResponseEntity<?> createNewTask(@RequestBody TaskRequest taskRequest) {
        try {
            ArrayList<TagDTO> tags = new ArrayList<>();
            for (String name : taskRequest.getTags()) {
                tags.add(tagService.getTag(DEMO_ID, name));
            }
            return ResponseEntity.ok(
                    TaskData.fromDTO(
                            taskRequest.getTaskData().getHolder(),
                            taskService.createNewTask(
                                    projectService.getProjectEntity(
                                            new HierarchicalId(DEMO_ID,
                                                    taskRequest.getTaskData().getHolder())),
                                    TaskDTO.fromData(taskRequest.getTaskData()),
                                    tags.toArray(new TagDTO[0]))
                    )
            );
        } catch (MinddyException e) {
            System.out.println("ERRRRR! " + e.getErrorMessage());
            return ResponseEntity.badRequest().body(getErrorResponse(e));
        }
    }

}
