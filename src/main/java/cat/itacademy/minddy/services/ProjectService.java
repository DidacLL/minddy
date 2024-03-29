package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.html.ProjectStructure;
import cat.itacademy.minddy.utils.MinddyException;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {

    /**
     * Internal method that creates the root project for a new user
     *
     * @param userId   the new user id
     * @param uiConfig frontend configuration (json string)
     * @param userName the new user preferred name
     * @param rootTag Root TagDTO from current user
     * @return the root ProjectDTO
     */
    ProjectDTO createRootProject(String userId, String uiConfig, String userName, TagDTO rootTag);

    /**Create Project, needs a dto filled with all not null values
     *  except id.ownId that is set automatically by this method
     * @param dto fullfilled DTO
     * @return resulting project DTO if success
     * @throws MinddyException otherwise
     */
    ProjectDTO createProject(ProjectDTO dto, TagDTO ... tags) throws MinddyException;

    /**Method that retrieves the project structure
     * @param userID UserID obtained from authentication
     * @param today Today date sent by front-end to keep user Locale
     * @return ProjectStructure that contains all projects from user (does not include DISCARDED or COMPLETED projects)
     */
    ProjectStructure getProjectStructure(String userID, LocalDate today) throws MinddyException;

    /**Internal method that looks for the projectDTO
     * @param hierarchicalId UserID, HolderID and own ID
     * @return ProjectDTO if found
     * @throws MinddyException if not found
     */
    ProjectDTO getProject(HierarchicalId hierarchicalId) throws MinddyException;


    ProjectDTO updateProject(ProjectDTO dto, TagDTO... tags) throws MinddyException;
    List<ProjectDTO> getAllSubProjects(HierarchicalId hierarchicalId);
    List<String> getAllSubprojectsID(HierarchicalId hierarchicalId);
    void deleteProject(HierarchicalId hierarchicalId);

    Project getProjectEntity(HierarchicalId projectId) throws MinddyException;

    List<String> getNearToDeadLine(String userId, LocalDate today);
}
