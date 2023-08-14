package cat.itacademy.minddy.services;


import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.NoteFullView;
import cat.itacademy.minddy.data.dto.views.NoteMinimal;
import cat.itacademy.minddy.utils.MinddyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;
@DirtiesContext
@Sql(scripts = "/testscript.sql")
@SpringBootTest
class NoteServiceTest {
    @Autowired
    NoteService service;
    @Autowired
    TagService tagService;
    static HierarchicalId projectId = new HierarchicalId("1234567891", "00FFFF");
    static String taskID = "10a6f4fc-b187-40be-9788-84c6f0093747";

    @Test
    @DisplayName(value = "Create New Note")
    void createNewNote_test() {
        assertDoesNotThrow(() -> {
            var note = service.createNewNote(projectId,
                    new NoteDTO().setName("TEST").setBody("This is a test note").setVisible(true).setType(NoteType.TEXT), tagService.getTag(projectId.getUserId(), "PATATA"));
            assertTrue("Checking persisted entity ", service.getNote(projectId, (note.getId())).getName().equalsIgnoreCase("test"));
        });

    }

    @Test
    @DisplayName("Create Task Note")
    void createTaskNote_test() {
        assertDoesNotThrow(() -> assertEquals(taskID, service.createTaskNote(projectId,
                taskID,
                new NoteDTO()
                        .setName("TEST")
                        .setBody("This is a test note")
                        .setVisible(true)
                        .setType(NoteType.TEXT)
//                            .setTags(List.of(tagService.getTag(projectId.getUserId(), "FRITA")))
        ).getName()));

    }

    @Test
    @DisplayName("Update Note")
    void updateNote_test() {
        var ref = new Object() {
            NoteDTO note;
        };
        assertDoesNotThrow(() -> {
            ref.note = service.createNewNote(projectId,
                    new NoteDTO().setName("DELETE ME").setBody("This is a test note").setVisible(true).setType(NoteType.TEXT), tagService.getTag(projectId.getUserId(), "PATATA"));
            assertTrue("Checking persisted entity ", service.getNote(projectId, (ref.note.getId())).getName().equalsIgnoreCase("delete me"));
        });
        assertDoesNotThrow(() -> {
            service.updateNote(projectId,
                    service.getNote(projectId, ref.note.getId())
                            .setName("SUCCESS")
            );
            assertEquals("SUCCESS", service.getNote(projectId, ref.note.getId()).getName());
        });
    }

    @Test
    @DisplayName("Delete Note")
    void deleteNote_test() {
        var ref = new Object() {
            NoteDTO note;
        };
        assertDoesNotThrow(() -> {
            ref.note = service.createNewNote(projectId,
                    new NoteDTO().setName("DELETE ME").setBody("This is a test note").setVisible(true).setType(NoteType.TEXT), tagService.getTag(projectId.getUserId(), "PATATA"));
            assertTrue("Checking persisted entity ", service.getNote(projectId, (ref.note.getId())).getName().equalsIgnoreCase("delete me"));
        });
        assertDoesNotThrow(() -> service.deleteNote(projectId, ref.note.getId()).getName());
        assertThrows(MinddyException.class, () -> service.getNote(projectId, ref.note.getId()));
    }

    @Test
    @DisplayName("Get all user visible Notes")
    void getAllVisibleNotes_test() {
        assertDoesNotThrow(() -> {
            Page<NoteMinimal> allVisibleNotes = service.getAllVisibleNotes(projectId, 0, Integer.MAX_VALUE);
            for (NoteMinimal note : allVisibleNotes) System.out.println(note.getName());
            assertTrue("Checking default sql script number of notes", allVisibleNotes.stream().toList().size() == 3);
        });
    }

    @Test
    void getSystemNotes_test() {
        assertDoesNotThrow(() -> {
            List<NoteDTO> allVisibleNotes = service.getSystemNotes(new HierarchicalId(projectId.getUserId(), "", "00"));
            for (NoteDTO note : allVisibleNotes) System.out.println(note.getName());
            assertTrue("Checking default sql script number of notes", allVisibleNotes.stream().toList().size()==1);
        });
    }

    @Test
    void getTaskNotes_test() {
        assertDoesNotThrow(() -> {
            assertEquals(taskID, service.createTaskNote(projectId,
                    taskID,
                    new NoteDTO()
                            .setName("TEST")
                            .setBody("This is a test note")
                            .setVisible(true)
                            .setType(NoteType.TEXT)
            ).getName());
            assertEquals(taskID, service.createTaskNote(projectId,
                    taskID,
                    new NoteDTO()
                            .setName("TEST")
                            .setBody("This is a test note")
                            .setVisible(true)
                            .setType(NoteType.TEXT)
            ).getName());

        });
        assertDoesNotThrow(() -> {
            List<NoteDTO> taskNotes = service.getTaskNotes(projectId, taskID);
            for (var note : taskNotes) System.out.println(note.getName());
            assertTrue("Unexpected taskNotes number", taskNotes.size() >= 2);
        });
    }

    @Test
    void getFullNote_test() {
        var tags = new ArrayList<TagDTO>();
        for (int i = 0; i < 5; i++) {
            try {
                tags.add(tagService.createTag(projectId.getUserId(), new TagDTO().setName("tag_test_" + i).setVisible(true).setHeritable(false)));
            } catch (MinddyException e) {
                throw new RuntimeException(e);
            }
        }
        var ref = new Object() {
            NoteDTO note;
        };
        assertDoesNotThrow(() -> {
            ref.note = service.createNewNote(
                    projectId,
                    new NoteDTO().setName("DELETE ME").setBody("This is a test note").setVisible(true).setType(NoteType.TEXT),
                    tags.toArray(TagDTO[]::new));
            assertTrue("Checking persisted entity ", service.getNote(projectId,(ref.note.getId()))
                    .getName().equalsIgnoreCase("delete me"));
        });
        assertDoesNotThrow(() ->
                {
                    NoteFullView fullNote = service.getFullNote(projectId, ref.note.getId());
                    assertTrue("Unable to load full view Note", fullNote.getTags().size() == 5);
                }
        );
    }

    @Test
    void searchNotesByTag_test() {
        var ref = new Object() {
            final List<NoteDTO> note=new ArrayList<>();
        };
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                ref.note.add(service.createNewNote(
                        projectId,
                        new NoteDTO().setName("DELETE ME_"+i).setBody("This is a test note").setVisible(true).setType(NoteType.TEXT),
                        tagService.getTag(projectId.getUserId(),"PATATA")));
                assertTrue("Checking persisted entity ", service.getNote(projectId,ref.note.get(ref.note.size()-1).getId())
                        .getName().equalsIgnoreCase("delete me_"+i));
            }
            assertTrue("",service.searchNotesByTag(projectId,0,Integer.MAX_VALUE, List.of("PATATA").toArray(String[]::new),NoteType.TEXT).stream().count()>=5);
        });

    }

    @Test
    void searchNotesByName_test() {
    }

    @Test
    void searchNotesByContent_test() {
    }

}
