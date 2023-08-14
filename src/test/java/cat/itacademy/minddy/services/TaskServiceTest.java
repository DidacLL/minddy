package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.Priority;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dto.TaskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext
@SpringBootTest
@Sql(scripts = "/testscript.sql")
class TaskServiceTest {
    @Autowired
    TaskService taskService;
    @Autowired
    ProjectService projectService;
    static HierarchicalId projectId = new HierarchicalId("1234567890", "00FFFF");
    static UUID taskID = UUID.fromString("10a6f4fc-b187-40be-9788-84c6f0093747");
    static int projectTasks= 6;
    @Autowired
    TagService tagService;

    @Test
    @DisplayName("Get all tasks from a project")
    void getAllProjectMinTasks_all_test() {
        assertDoesNotThrow(()->{
           var res= taskService.getAllProjectMinTasks(projectId).stream().toList();
            System.out.println(res.size());
           for (var tm : res) System.out.println(tm.getName());
           assertTrue(res.size()>=5);
        });

    }
    @Test
    @DisplayName("Get all tasks from a project which state is not X")
    void getAllProjectMinTasks_notDiscarded_test() {
        assertDoesNotThrow(()->{
           var res= taskService.getAllProjectMinTasks(projectId,TaskState.DISCARDED).stream().toList();
            System.out.println(res.size());
           for (var tm : res) {
               System.out.println(tm.getName());
               assertFalse(tm.getName().equalsIgnoreCase("Test Discarded"));
           }
        });

    }
    @Test
    @DisplayName("Get all tasks from a project which state is not in X[]")
    void getAllProjectMinTasks_notInList_test() {
        assertDoesNotThrow(()->{
           var res= taskService.getAllProjectMinTasks(projectId,TaskState.TODO,TaskState.DONE,TaskState.DEFERRED,TaskState.ON_PROGRESS, TaskState.REVIEW).stream().toList();
            System.out.println(res.size());
           for (var tm : res) System.out.println(tm.getName());
            assertEquals(1, res.size());
        });

    }


    @Test
    void getProjectExpandedTasks_all_test() {
        assertDoesNotThrow(()-> {
            var res = taskService.getProjectExpandedTasks(projectId, 10, 0).stream().toList();
            System.out.println(res.size());
            for (var te : res) System.out.println(te.getName()+ ": "+te.getDescription());
            assertEquals(projectTasks,res.size());
        });

    }


    @Test
    void getProjectExpandedTasks_notInX_test() {
        assertDoesNotThrow(()-> {
            var res = taskService.getProjectExpandedTasks(projectId, 10, 0,TaskState.DISCARDED).stream().toList();
            System.out.println(res.size());
            for (var te : res) System.out.println(te.getName()+ ": "+te.getDescription());
            assertEquals(projectTasks-1,res.size());
        });

    }


    @Test
    void getProjectExpandedTasks_notInList_test() {
        assertDoesNotThrow(()-> {
            var res = taskService.getProjectExpandedTasks(projectId, 10, 0,TaskState.TODO,TaskState.DONE,TaskState.DEFERRED,TaskState.ON_PROGRESS, TaskState.REVIEW).stream().toList();
            System.out.println(res.size());
            for (var te : res) System.out.println(te.getName()+ ": "+te.getDescription());
            assertEquals(1,res.size());
        });

    }

    @Test
    void getMinimalTask_test() {
        assertDoesNotThrow(()-> System.out.println(taskService.getMinimalTask(projectId.getUserId(),taskID).getName()));
    }

    @Test
    void getExpandedTask_test() {
        assertDoesNotThrow(()-> System.out.println(taskService.getExpandedTask(projectId.getUserId(),taskID).getName()));

    }

    @Test
    void getTask_test() {
        assertDoesNotThrow(()-> System.out.println(taskService.getTask(projectId.getUserId(),taskID).getName()));

    }

    @Test
    void createNewTask_test() {
        assertDoesNotThrow(()->{
            var project = projectService.getProjectEntity(projectId);
            var res = taskService.createNewTask(project,
                    new TaskDTO("Created Test","gdfhbdfhb dfsg df dsg dfxgh sd",TaskState.DONE, LocalDate.now(), Priority.HIGHER),
                    tagService.getTag(projectId.getUserId(),"test1"),
                    tagService.getTag(projectId.getUserId(),"test2"));
            System.out.println(res.getId());
        });
    }

    @Test
    void updateTask_test() {
    }

    @Test
    void deleteTask_test() {
    }

    @Test
    void countUserTasks_all_test() {
        var val= taskService.countUserTasks(projectId.getUserId());
        System.out.println(val);
        assertEquals(11,val);
    }
    @Test
    void countUserTasks_notInX_test() {
        var val= taskService.countUserTasks(projectId.getUserId(),TaskState.DISCARDED);
        System.out.println(val);
        assertEquals(10,val);
    }
    @Test
    void countUserTasks_notInList_test() {
        var val= taskService.countUserTasks(projectId.getUserId(),TaskState.TODO,TaskState.DONE,TaskState.DEFERRED,TaskState.ON_PROGRESS, TaskState.REVIEW);
        System.out.println(val);
        assertEquals(1,val);
    }

    @Test
    @DisplayName("Count all tasks in project")
    void countAllProjectTasks_test() {
        var val= taskService.countProjectTasks(projectId);
        System.out.println(val);
        assertTrue(val>=projectTasks);
    }

    @Test
    @DisplayName("Count all tasks in project not in X state")
    void countStateProjectTasks_test() {
        var val= taskService.countProjectTasks(projectId,TaskState.DISCARDED);
        System.out.println(val);
        assertTrue(val<projectTasks);
    }
    @Test
    @DisplayName("Count all tasks in project not in X[] states")
    void countStateArrayProjectTasks_test() {
        var val= taskService.countProjectTasks(projectId,TaskState.TODO,TaskState.REVIEW,TaskState.DONE, TaskState.ON_PROGRESS);

        assertEquals(1, val);
        System.out.println(val);
    }
    @Test
    void getTodayTasks_test() {
    }
}
