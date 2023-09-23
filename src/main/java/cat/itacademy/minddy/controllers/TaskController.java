package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.dto.views.ProjectData;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Project Service",description = "Provides project information")
public interface TaskController {


    @GetMapping("/task/data")
    ResponseEntity<?> getFullTask(Authentication auth, @RequestParam String id);

    @GetMapping("task/demo/data")
    ResponseEntity<?> getFullTask(@RequestParam String id);

    ResponseEntity<?> getTaskTags(Authentication auth, String id);

    ResponseEntity<?> getTaskTags(String id);

    ResponseEntity<?> updateProject(Authentication auth, ProjectData projectData);

    ResponseEntity<?> updateProject(ProjectData projectData);
}