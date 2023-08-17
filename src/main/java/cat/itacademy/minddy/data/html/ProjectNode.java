package cat.itacademy.minddy.data.html;

import cat.itacademy.minddy.data.dto.views.ProjectMinimal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ProjectNode {
    ProjectMinimal project;
    List<ProjectNode> subProjects=new ArrayList<>();
    int accumulatedSubprojects=0;

    public ProjectNode(ProjectMinimal project) {
        this.project = project;
    }

    public ProjectNode setSubProjects(List<ProjectMinimal> immediateSubprojects) {
        for(var p: immediateSubprojects)this.subProjects.add(new ProjectNode(p));
        return this;
    }

    public ProjectNode addChild(ProjectNode node) {
        this.subProjects.add(node);
        this.accumulatedSubprojects+=1+ node.accumulatedSubprojects;
        return this;
    }
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();

        jsonNode.put("project", mapper.valueToTree(this.project));
        jsonNode.put("accumulatedSubprojects", this.accumulatedSubprojects);

        ObjectNode subProjectsNode = mapper.createObjectNode();
        for (int i = 0; i < this.subProjects.size(); i++) {
            subProjectsNode.set(String.valueOf(i), mapper.readTree(this.subProjects.get(i).toJson()));
        }
        jsonNode.set("subProjects", subProjectsNode);

        return jsonNode.toString();
    }
}
