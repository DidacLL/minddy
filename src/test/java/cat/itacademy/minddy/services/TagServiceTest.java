package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.utils.MinddyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext
@SpringBootTest
@Sql(scripts = "/testscript.sql")
class TagServiceTest {
    @Autowired
    TagService tagService;
    @Autowired
    ProjectService projectService;
    @Autowired
    NoteService noteService;
    static HierarchicalId projectId = new HierarchicalId("1234567890", "00FFFFFF");
    static String taskID = "10a6f4fc-b187-40be-9788-84c6f0093747";

    @Test
    @DisplayName("Default get tagDTO by ID")
    void getTag_test() {
        assertDoesNotThrow(() -> System.out.println(tagService.getTag(projectId.getUserId(), "PATATA").getName()));
    }

    @Test
    @DisplayName("Get tag full entity by ID")
    void getTagEntity_test() {
        assertDoesNotThrow(() -> System.out.println(tagService.getTagEntity(projectId.getUserId(), "PATATA").getId().getName()));

    }

    @Test
    @DisplayName("Get all tags in a project")
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
    @DisplayName("Get all tags(full entity) from a project ")
    void getProjectTagsEntity_test() {
        assertDoesNotThrow(()-> {
            var tags = new ArrayList<TagDTO>();
            for (int i = 0; i < 5; i++) tags.add(TagDTO.fromEntity(tagService.createTag(projectId.getUserId(),
                    "TEST_P" + i)));
            projectService.updateProject(projectService.getProject(projectId),tags.toArray(TagDTO[]::new));
//            .setTags(tags));
            assertTrue(tagService.getProjectTagsEntity(projectId,true).size()>=5);
        });
    }

    @Test
    @DisplayName("Get all tagDTOs in a Note")
    void getNoteTags_test() {
        assertDoesNotThrow(()-> {
            UUID id = noteService.getNote(projectId, UUID.fromString("58a6f4fc-b187-40be-9788-84c6f0093747")).getId();
            System.out.println(id);
            List<TagDTO> noteTags = tagService.getNoteTags(projectId.getUserId(),
                    id,
                    true);
            System.out.println("Found "+ noteTags.size() + " Note Tags");
            assertTrue(noteTags.size() >= 6);
        });
    }

    @Test
    @DisplayName("Get all tags(full entity) from a note ")
    void getNoteTagsEntity_test() {
        assertDoesNotThrow(()-> {
            List<Tag> noteTagsEntity = tagService.getNoteTagsEntity(projectId.getUserId(),
                    UUID.fromString("58a6f4fc-b187-40be-9788-84c6f0093747"));
            System.out.println("Found " + noteTagsEntity.size()+" Note Tags");
            assertTrue(noteTagsEntity.size() >= 6);
        });
    }

    @Test
    @DisplayName("Get all tagDTOs in a Task")
    void getTaskTags_test() {
        assertDoesNotThrow(()-> {
            List<TagDTO> taskTags = tagService.getTaskTags(projectId.getUserId(),
                    UUID.fromString("10a6f4fc-b187-40be-9788-84c6f0093747"), true);
            System.out.println("Found " + taskTags.size()+" Task Tags");
            assertTrue(taskTags.size() >= 3);
        });
    }

    @Test
    @DisplayName("Get all tags(full entity) from a task ")
    void getTaskTagsEntity_test() {
        assertDoesNotThrow(()-> {
            List<Tag> taskTags = tagService.getTaskTagsEntity(projectId.getUserId(),
                    UUID.fromString("10a6f4fc-b187-40be-9788-84c6f0093747"), true);
            System.out.println("Found " + taskTags.size()+" Task Tags");
            assertTrue(taskTags.size() >= 3);
        });
    }

    @Test
    @DisplayName("Get all tag names that contains X string")

    void searchTagsLike_test() {
        assertDoesNotThrow(()-> {
            assertTrue(tagService.searchTagsLike(projectId.getUserId(),
                   "tes").size() >= 6);
            assertFalse(tagService.searchTagsLike(projectId.getUserId(),
                    "pa").isEmpty());
        });
    }

    @Test
    @DisplayName("Count number of tag usages")
    void countTagUses_test() {
        assertDoesNotThrow(()->{
            int test1 = tagService.countTagUses(projectId.getUserId(), "test1");
            System.out.println("1= "+test1);
            int test6 = tagService.countTagUses(projectId.getUserId(), "test6");
            System.out.println("6= "+test6);
            assertTrue(test1 > test6);
        });
    }

    @Test
    @DisplayName("Create new default tag ")
    void createTagByName_test() {
        assertDoesNotThrow(()-> {
            tagService.createTag(projectId.getUserId(),"Escoria");
            assertTrue(tagService.getTag(projectId.getUserId(),
                    "Escoria").isVisible());
        });
    }

    @Test
    void updateTag_test() {
        assertDoesNotThrow(()-> {
            tagService.createTag(projectId.getUserId(),new TagDTO("Escoria2",false,false));
            tagService.updateTag(projectId.getUserId(),"Escoria2",new TagDTO("Pepitoria2",true,true));
            TagDTO escoria2 = tagService.getTag(projectId.getUserId(),"Pepitoria2");
            assertTrue(escoria2.isVisible());
        });
    }

    @Test
    void deleteTag_test() {
        assertDoesNotThrow(()-> {
            var dto=TagDTO.fromEntity(tagService.createTag(projectId.getUserId(),"Escoria3"));
            assertTrue(tagService.getTag(projectId.getUserId(),
                    "Escoria3").isVisible());
            tagService.deleteTag(projectId.getUserId(),dto);
            assertThrows(MinddyException.class,()->tagService.getTag(projectId.getUserId(),"Escoria3"));

        });
    }
}
