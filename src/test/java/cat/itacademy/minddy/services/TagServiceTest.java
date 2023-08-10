package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dto.TagDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "/testscript.sql")
class TagServiceTest {
    @Autowired
    TagService tagService;
    @Autowired
    ProjectService projectService;
    static HierarchicalId projectId = new HierarchicalId("1234567890", "00FFFF");
    static String taskID = "10a6f4fc-b187-40be-9788-84c6f0093747";

    @Test
    @DisplayName("Defaul get tag by ID")
    void getTag_test() {
        assertDoesNotThrow(() -> System.out.println(tagService.getTag(projectId.getUserId(), "PATATA").getName()));
    }

    @Test
    void getTagEntity_test() {
        assertDoesNotThrow(() -> System.out.println(tagService.getTagEntity(projectId.getUserId(), "PATATA").getId().getName()));

    }

    @Test
    void getProjectTags_test() {
        assertDoesNotThrow(()-> {
            var tags = new ArrayList<TagDTO>();
            for (int i = 0; i < 5; i++) tags.add(TagDTO.fromEntity(tagService.createTag(projectId.getUserId(), "TEST_P" + i)));
            projectService.updateProject(projectService.getProject(projectId),tags.toArray(TagDTO[]::new));
//            .setTags(tags));
            assertTrue(tagService.getProjectTags(projectId,true).size()>=5);
        });
    }

    @Test
    void getNoteTags_test() {
    }

    @Test
    void getTaskTags_test() {
    }

    @Test
    void getTagsLike_test() {
    }

    @Test
    void countTagUses_test() {
    }

    @Test
    void createTag_test() {
    }

    @Test
    void testGetNoteTags_test() {
    }

    @Test
    void getTagsFromList_test() {
    }

    @Test
    void testCreateTag_test() {
    }

    @Test
    void testGetTagsFromList_test() {
    }

    @Test
    void loadTags_test() {
    }
}
