package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.DefaultTags;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.utils.MinddyException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@SpringBootTest
@Sql(scripts = "/testscript.sql")
class ProjectServiceTest {
    static String userId = "1234567890";
    static HierarchicalId parent = new HierarchicalId("1234567890", "00FFFF");
    @Autowired
    ProjectService projectService;
    @Autowired
    TagService tagService;

    @Test
    void createProject_test() {
        assertDoesNotThrow(() -> {
            var saved = projectService.createProject(
                    parent.generateChildBase().setName("NEW SUBPROJECT!").setDescription("just another test project"),
                    tagService.getTag(userId, "PATATA"), tagService.getTag(userId, "FRITA")
            );
            assertTrue(saved.isFullFilled());
            System.out.println(saved.getId());
            assertTrue(saved.getId().getOwnId().equalsIgnoreCase("FE"));
            saved = projectService.createProject(
                    parent.generateChildBase().setName("NEW SUBPROJECT!").setDescription("just another test project")
                            .setDeadLine(LocalDate.of(2028, 11, 6)),
                    tagService.getTag(userId, "PATATA"), tagService.getTag(userId, "FRITA")
            );
            assertTrue(saved.isFullFilled());
            assertTrue(saved.getDeadLine().minusYears(8).isBefore(LocalDate.now()));
            System.out.println(saved.getId());
            assertTrue(saved.getId().getOwnId().equalsIgnoreCase("FD"));
        });
    }

    @Test
    void getProjectStructure_test() {
        var res = projectService.getProjectStructure(userId, LocalDate.now());
        for (var p : res.getData()) System.out.println(p.getOwnerID() + p.getProjectID() + ": " + p.getProjectName());
        assertTrue(res.getData().size() >= 5);
    }

    @Test
    void getProject_test() {
        assertDoesNotThrow(() -> assertTrue(projectService.getProject(parent).isFullFilled()));
    }

    @Test
    void updateProject_noTags_test() {
        assertDoesNotThrow(() -> {
            var old = projectService.getProject(parent);
            var updated = projectService.updateProject(old.setName("UPDATED").setState(ProjectState.COMPLETE));
            assertTrue(updated.getName().equalsIgnoreCase("updated") && updated.getState() == ProjectState.COMPLETE);
        });
    }

    @Test
    void updateProject_oneTag_test() {
        assertDoesNotThrow(() -> {
            var old = projectService.getProject(parent);
            var oldTags= tagService.getProjectTags(old.getId(),true).size();
            oldTags+=(tagService.getProjectTags(old.getId(),false).size());
            System.out.println(oldTags);
            var updated = projectService.updateProject(old.setName("UPDATED").setState(ProjectState.COMPLETE), tagService.getTag(userId, DefaultTags.FAVOURITE.getTagName()));
            assertTrue(updated.getName().equalsIgnoreCase("updated") && updated.getState() == ProjectState.COMPLETE);
            var updatedTags = tagService.getProjectTags(parent, true).size();
            updatedTags+=(tagService.getProjectTags(parent, false).size());
            System.out.println(updatedTags);
            assertEquals(1,updatedTags-oldTags);
        });
    }

    @Test
    void updateProject_fewTags_test() {
        assertDoesNotThrow(() -> {
            var old = projectService.getProject(parent);
            var oldTags= tagService.getProjectTags(old.getId(),true).size();
            oldTags+=(tagService.getProjectTags(old.getId(),false).size());

            var updated = projectService.updateProject(old.setName("UPDATED").setState(ProjectState.COMPLETE), tagService.getTag(userId, DefaultTags.FAVOURITE.getTagName()), tagService.getTag(userId, DefaultTags.DELAYED.getTagName()));
            assertTrue(updated.getName().equalsIgnoreCase("updated") && updated.getState() == ProjectState.COMPLETE);
            var updatedTags = tagService.getProjectTags(parent, true).size();
            updatedTags+=(tagService.getProjectTags(parent, false).size());
            assertEquals(2,updatedTags-oldTags);
        });
    }

    @Test
    void getAllSubProjects_test() {
        String holderId = parent.getHolderId();
        var holder = new HierarchicalId(userId, holderId);
        System.out.println("Searching subProjects from "+holder);
        List<ProjectDTO> subProjects = projectService.getAllSubProjects(holder);
        for(var sb:subProjects)System.out.println(sb.getId());
        assertTrue(subProjects.size()>=3);
    }

    @Test
    void getAllSubprojectsID_test() {
        String holderId = parent.getHolderId();
        var holder = new HierarchicalId(userId, holderId);
        System.out.println("Searching subProjects from "+holder);
        List<String> subProjects = projectService.getAllSubprojectsID(holder);
        for(var sb:subProjects)System.out.println(sb);
        assertTrue(subProjects.size()>=3);
    }

    @Test
    void deleteProject_test() {
        assertDoesNotThrow(() -> {
            var saved = projectService.createProject(
                    new HierarchicalId(userId,parent.getHolderId()).generateChildBase().setName("DELETE ME").setDescription("just another test project"),
                    tagService.getTag(userId, "PATATA"), tagService.getTag(userId, "FRITA")
            );
            assertTrue(saved.isFullFilled());
            assertTrue(projectService.getProject(saved.getId()).getName().equalsIgnoreCase("delete me"));
            projectService.deleteProject(saved.getId());
            assertThrows(MinddyException.class,()->projectService.getProject(saved.getId()));
        });

    }

    @Test
    void getProjectEntity_test() {
        assertDoesNotThrow(() -> assertTrue(projectService.getProject(parent).isFullFilled()));
    }
}
