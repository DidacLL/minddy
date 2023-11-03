package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.dto.views.ProjectRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Project Service",description = "Provides project information")
public interface ProjectController {


    @GetMapping("/data")
    ResponseEntity<?> getFullProject(Authentication auth, @RequestBody String projectFullId);

    @GetMapping("/demo/data")
    ResponseEntity<?> getFullProject(@RequestBody String projectFullId);


    ResponseEntity<?> getProjectPriorityTasks(Authentication auth, @RequestParam String id, @RequestParam int size, @RequestParam int page);

    ResponseEntity<?> getProjectPriorityTasks(@RequestParam String id, @RequestParam int size, @RequestParam int page);

    @GetMapping("/tag")
    ResponseEntity<?> getProjectTags(Authentication auth, @RequestParam String id);

    @GetMapping("demo/tag")
    ResponseEntity<?> getProjectTags(@RequestParam String id);

    @GetMapping("/demo/todo")
    ResponseEntity<?> getProjectTasks(Authentication auth, @RequestParam String id, @RequestParam int size, @RequestParam int page, @RequestParam boolean viewAll, @RequestParam boolean subproject);

    @GetMapping("/demo/tasks")
    ResponseEntity<?> getProjectTasks(@RequestParam String id, @RequestParam int size, @RequestParam int page, @RequestParam(name = "viewall") boolean viewAll, @RequestParam boolean subproject);

    ResponseEntity<?> updateProject(Authentication auth, ProjectRequest projectRequest);

    ResponseEntity<?> updateProject(ProjectRequest projectRequest);

    @PutMapping("/new")
    ResponseEntity<?> createNewProject(Authentication auth, @RequestBody ProjectRequest projectRequest);

    @PutMapping("/demo/new")
    ResponseEntity<?> createNewProject(@RequestBody ProjectRequest projectRequest);
}
