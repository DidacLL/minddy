package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TaskExpanded;
import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.UUID;

public interface TaskService {
    Page<TaskMinimal> getProjectPendingMinTasks(HierarchicalId projectId, int pageSize, int page)throws MinddyException;
    Page<TaskMinimal> getAllProjectMinTasks(HierarchicalId projectId, TaskState ... notIn)throws MinddyException;

    Page<TaskMinimal> getTodayTasks(String userID, LocalDate date, int pageSize, int page) throws MinddyException;

    Page<TaskExpanded> getProjectExpandedTasks(HierarchicalId projectId, int pageSize, int page, TaskState... notIn)throws MinddyException;

    TaskMinimal getMinimalTask(String userId,String taskId) throws MinddyException;
    TaskExpanded getExpandedTask(String userId,String taskId) throws MinddyException;
    TaskDTO getTask(String userId,String taskId) throws MinddyException;

    TaskDTO createNewTask(HierarchicalId holderID,TaskDTO dto) throws MinddyException;

    TaskDTO updateTask(HierarchicalId holderID, TaskDTO dto) throws MinddyException;

    void deleteTask(HierarchicalId holderId,UUID id) throws MinddyException;

    int countUserTasks(String userId, TaskState... notIn);
    int countProjectTasks(HierarchicalId projectId, TaskState... notIn);


}
