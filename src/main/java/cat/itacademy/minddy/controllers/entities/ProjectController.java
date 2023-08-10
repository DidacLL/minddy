package cat.itacademy.minddy.controllers.entities;

import cat.itacademy.minddy.data.dto.ProjectDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectController {
    //------------------------------------------------------------GET
    ResponseEntity<ProjectDTO> getProject( String id);
    ResponseEntity<List<ProjectDTO>> getSubProjects( String id);

    //-------------------------------------------------------------CREATE
    ResponseEntity<ProjectDTO> createSubProject( ProjectDTO dto);

    //-------------------------------------------------------------UPDATE
    ResponseEntity<Boolean> updateProject( ProjectDTO dto);
    ResponseEntity<Boolean> moveProject(ProjectDTO dto);// TODO: 03/07/2023 Must change holderId for all subprojects, tasks and notes...

    //-------------------------------------------------------------DELETE


}
