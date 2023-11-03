package cat.itacademy.minddy.data.dto.views;

import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProjectRequest {
    private ProjectData projectData;
    private String[] tags;

    public static ProjectRequest fromEntity(Project entity){
        return new ProjectRequest(
              ProjectData.fromEntity(entity), (String[]) entity.getTags().stream().map(t-> t.getId().getName()).toArray()
        );
    }
    public ProjectDTO getDTO(String userId){
        return ProjectDTO.fromData(userId,this.projectData);
    }
}
