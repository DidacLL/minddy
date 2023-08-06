package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.services.NoteService;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.utils.MinddyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(scripts = "/testscript.sql")
@SpringBootTest
class NoteServiceImplTest {
    @Autowired
    NoteService service;
    @Autowired
    TagService tagService;

    @Test
    @DisplayName(value = "Create New Note")
    void createNewNote_test() {
        try {
            service.createNewNote(new HierarchicalId("1234567890", "00FFFF"),
                    new NoteDTO().setName("TEST").setBody("This is a test note").setVisible(true).setType(NoteType.TEXT).setTags(List.of(tagService.getTag("1234567890","PATATA"))));
            //fixme excpetion comes from tag copy in NoteDTO

        } catch (MinddyException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test
    void createTaskNote_test() {
    }

    @Test
    void updateNote_test() {
    }

    @Test
    void deleteNote_test() {
    }

    @Test
    void getAllVisibleNotes_test() {
    }

    @Test
    void getSystemNotes_test() {
    }

    @Test
    void getTaskNotes_test() {
    }

    @Test
    void getFullNote_test() {
    }

    @Test
    void searchNotesByTag_test() {
    }

    @Test
    void searchNotesByName_test() {
    }

    @Test
    void searchNotesByContent_test() {
    }
}
