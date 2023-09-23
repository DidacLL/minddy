package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TaskData;
import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskService {
    Page<TaskMinimal> getProjectPendingMinTasks(HierarchicalId projectId, int pageSize, int page)throws MinddyException;
    Page<TaskMinimal> getAllProjectMinTasks(HierarchicalId projectId, TaskState ... notIn)throws MinddyException;

    Page<TaskMinimal> getTodayTasks(String userID, LocalDate date, int pageSize, int page) throws MinddyException;

    Page<TaskData> getProjectExpandedTasks(HierarchicalId projectId, int pageSize, int page, TaskState... notIn)throws MinddyException;

    Page<TaskData> getProjectExpandedTasksExclusive(HierarchicalId projectId, int pageSize, int page, TaskState... notIn);
    TaskMinimal getMinimalTask(String userId, UUID taskId) throws MinddyException;
    TaskData getExpandedTask(String userId, UUID taskId) throws MinddyException;

    TaskDTO getTask(String userId, UUID taskId) throws MinddyException;

    TaskDTO createNewTask(Project project, TaskDTO dto, TagDTO... tags) throws MinddyException;

    TaskDTO updateTask(HierarchicalId holderID, TaskDTO dto, TagDTO ... tags) throws MinddyException;

    void deleteTask(HierarchicalId holderId,UUID id) throws MinddyException;

    int countUserTasks(String userId, TaskState... notIn);
    int countProjectTasks(HierarchicalId projectId, TaskState... notIn);


    List<TaskMinimal> getMissedTasks(String userId, LocalDate today);
}
