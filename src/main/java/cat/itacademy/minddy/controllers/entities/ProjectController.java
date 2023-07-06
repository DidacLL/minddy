package cat.itacademy.minddy.controllers.entities;

import cat.itacademy.minddy.data.dto.NewProjectDTO;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProjectController {
    //------------------------------------------------------------GET
    ResponseEntity<ProjectDTO> getProject(@RequestBody String id);
    ResponseEntity<List<ProjectDTO>> getSubProjects(@RequestBody String id);

    //-------------------------------------------------------------CREATE
    ResponseEntity<ProjectDTO> createSubProject(@RequestBody NewProjectDTO dto);

    //-------------------------------------------------------------UPDATE
    ResponseEntity<Boolean> updateUnliked(@RequestBody ProjectDTO dto);
    ResponseEntity<Boolean> moveProject(@RequestBody ProjectDTO dto);// TODO: 03/07/2023 Must change holderId for all subprojects, tasks and notes...

    //-------------------------------------------------------------DELETE


}
