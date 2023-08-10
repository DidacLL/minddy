package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.html.ProjectStructure;
import cat.itacademy.minddy.repositories.ProjectRepository;
import cat.itacademy.minddy.repositories.TagRepository;
import cat.itacademy.minddy.services.ProjectService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository repo;
    @Autowired
    TagRepository tagRepository;
    @Override
    public ProjectDTO createRootProject(String userId, String uiConfig, String userName, Tag rootTag) {

           var root= new ProjectDTO()
                   .setId(new HierarchicalId().setOwnId("00").setUserId(userId).setHolderId(""))
                   .setName(userName)
                   .setDescription("")
//                   .setTrackers(null)// TODO: 02/07/2023  Add trackers to count subprojects and tasks
                   .setUiConfig(uiConfig)
                   .setDeadLine(null)
                   .setState(ProjectState.SILENT);



        return ProjectDTO.fromEntity(repo.save(Project.fromDTO(root).addTag(rootTag)));
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto) throws MinddyException {
        if(!dto.isFullFilled())throw new MinddyException(400,"Request does not have proper data");
        //Find Parent
        var parent = repo.findById(new HierarchicalId(dto.getId().getUserId(),dto.getId().getHolderId()))
                .orElseThrow(()->new MinddyException(422,"Parent project error"));
        //Integrity Checks
        if(parent.getDeadLine()!=null && dto.getDeadLine().isBefore(parent.getDeadLine()))
            throw new MinddyException(422,"child deadLine may be greater than parent deadline");
        //Copy heritable tags
//FIXME        for(Tag tag: parent.getTags())if (tag.isHeritable()) dto.addTag(tag);
        //Copy parent uiConfig if not set explicitly
        if(dto.getUiConfig()==null || dto.getUiConfig().trim().isEmpty())dto.setUiConfig(parent.getUiConfig());
        //Assign ownID by obtaining last parent subproject ID
        var lastSubprojectID = repo.getLastSubprojectsID(dto.getId().getUserId(), dto.getId().getHolderId());
        String newId= lastSubprojectID.map(s -> Integer.toHexString((Integer.parseInt(s, 16) - 1)))
                .orElse("FF");
        dto.setId(dto.getId().setOwnId(newId));
        //Save it
        return ProjectDTO.fromEntity(repo.save(Project.fromDTO(dto)));
    }

    @Override
    public ProjectStructure getProjectStructure(String userID, LocalDate today) {
        return new ProjectStructure(
                today,
                repo.getAllProjectsMin(userID, ProjectState.DISCARDED,ProjectState.COMPLETE)
        );
    }

    @Override
    public ProjectDTO getProject(HierarchicalId hierarchicalId) throws MinddyException {
        return ProjectDTO.fromEntity(
                repo.findById(hierarchicalId).orElseThrow(
                        ()->new MinddyException(404, "Project " + hierarchicalId + " does not exists")
                )
        );
    }



    @Override
    public ProjectDTO updateProject(ProjectDTO dto, TagDTO... tags) throws MinddyException {
        var oldProject= getProject(dto.getId());
        Set<TagDTO> oldTags = tagRepository.getAllProjectTags(oldProject.getId().getUserId(), oldProject.getId().getHolderId(), oldProject.getId().getOwnId(), true).stream().map(TagDTO::fromEntity).collect(Collectors.toSet());
        oldTags.addAll(tagRepository.getAllProjectTags(oldProject.getId().getUserId(),oldProject.getId().getHolderId(),oldProject.getId().getOwnId(),false).stream().map(TagDTO::fromEntity).toList());
        //CHECK NAME
        if(dto.getName()!=null && !dto.getName().trim().isEmpty() && !dto.getName().equalsIgnoreCase(oldProject.getName()))
            oldProject.setName(dto.getName());
        //CHECK Description
        if(dto.getDescription()!=null && !dto.getDescription().trim().isEmpty() && !dto.getDescription().equalsIgnoreCase(oldProject.getDescription()))
            oldProject.setDescription(dto.getDescription());
        //CHECK State
        if(dto.getState()!=null && dto.getState()!=oldProject.getState())
            oldProject.setState(dto.getState());
        //CHECK TAGS
        if(tags!=null && tags.length>0)
            oldTags.addAll(List.of(tags));
        //CHECK DeadLine
        if(dto.getDeadLine()!=null && !dto.getDeadLine().equals(oldProject.getDeadLine())) {
            // FIXME: 02/08/2023 call to TagService to retrieve DELAYED TASK
            //  if(dto.getDeadLine().isAfter(oldProject.getDeadLine()))dto.addTag(DefaultTags.DELAYED);
            oldProject.setDeadLine(dto.getDeadLine());
        }
        
        //CHECK UIConfig
        if(dto.getUiConfig()!=null && !dto.getUiConfig().trim().isEmpty() && !dto.getUiConfig().equalsIgnoreCase(oldProject.getUiConfig()))
            oldProject.setUiConfig(dto.getUiConfig());


        return ProjectDTO.fromEntity(repo.save(Project.fromDTO(oldProject,oldTags.toArray(TagDTO[]::new))));
    }

    @Override
    public ProjectDTO getAllSubProjects(HierarchicalId hierarchicalId) {
        return null;
    }

    @Override
    public List<String> getAllSubprojectsID(HierarchicalId hierarchicalId) {
        return null;
    }

    @Override
    public void deleteProject(HierarchicalId hierarchicalId) {

    }
}
