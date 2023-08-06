package cat.itacademy.minddy.data.html;


import cat.itacademy.minddy.data.dto.views.ProjectMinimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProjectStructure {
    private LocalDate date;
    private List<ProjectMinimal> data;

    public void addProject(ProjectMinimal project){
        this.data.add(project);
    }

}
