package cat.itacademy.minddy.data.html;


import cat.itacademy.minddy.data.dto.views.ProjectMinimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProjectStructure {
    private LocalDate date;
    private ProjectNode projectTree;

    public ProjectStructure addProject(ProjectMinimal project){
        // TODO: 17/08/2023  
        return this;
    }
}
