package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TaskExpanded;
import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import cat.itacademy.minddy.repositories.TaskRepository;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.utils.MinddyException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository repo;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<TaskMinimal> getProjectPendingMinTasks(HierarchicalId projectId, int pageSize, int page) throws MinddyException {
        return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                TaskState.DONE,
                TaskState.DISCARDED
        );
    }

    @Override
    public Page<TaskMinimal> getAllProjectMinTasks(HierarchicalId projectId, TaskState... notIn) throws MinddyException {
        return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(0, Integer.MAX_VALUE),
                notIn
        );
    }

    @Override
    public Page<TaskMinimal> getTodayTasks(String userID, LocalDate date, int pageSize, int page) throws MinddyException {
        return repo.getTodayTasks(userID, date, PageRequest.of(page, pageSize));
    }

    @Override
    public Page<TaskExpanded> getProjectExpandedTasks(HierarchicalId projectId, int pageSize, int page, TaskState... notIn) throws MinddyException {
        return repo.getProjectExpandedTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                notIn);
    }

    @Override
    public TaskDTO createNewTask(HierarchicalId holderID, TaskDTO dto) throws MinddyException {
        try {
            Project project = em.getReference(Project.class, holderID);
            return TaskDTO.fromEntity(repo.save(Task.fromDTO(dto).setHolder(project)));
        } catch (Exception e) {
            throw new MinddyException(400, "Project holder not found");
        }
    }

    @Override
    public TaskMinimal getMinimalTask(String userId, String taskId) throws MinddyException {
        return repo.getMinimalTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found"));
    }

    @Override
    public TaskExpanded getExpandedTask(String userId, String taskId) throws MinddyException {
        return repo.getExpandedTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found"));
    }

    @Override
    public TaskDTO getTask(String userId, String taskId) throws MinddyException {
        return TaskDTO.fromEntity(repo.getTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found")));
    }

    @Override
    public TaskDTO updateTask(HierarchicalId holderID, TaskDTO dto) throws MinddyException {
        var old = repo.getTask(holderID.getUserId(), dto.getId().toString()).orElseThrow(() -> new MinddyException(404, "task " + holderID.toString() + " not found"));
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) old.setName(dto.getName());
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty())
            old.setDescription(dto.getDescription());
        if (dto.getState() != null && !dto.getState().equals(old.getState())) old.setState(dto.getState());
        if (dto.getPriority() != null && !dto.getPriority().equals(old.getPriority()))
            old.setPriority(dto.getPriority());

        //CHECK Date
        if (dto.getDate() != null && !dto.getDate().equals(old.getDate())) {
            // FIXME: 02/08/2023 call to TagService to retrieve DELAYED TASK
            //  if(dto.getDate().isAfter(old.getDate()))dto.addTag(DefaultTags.DELAYED);
            old.setDate(dto.getDate());
        }
        return TaskDTO.fromEntity(repo.save(old));
    }

    @Override
    public void deleteTask(HierarchicalId holderId, UUID id) throws MinddyException {
        repo.delete(
                repo.getTask(holderId.getUserId(), id.toString())
                        .orElseThrow(() -> new MinddyException(404, "task " + holderId.toString() + " not found"))
        );
    }

    @Override
    public int countUserTasks(String userId, TaskState... notIn) {
        return repo.countUserTasks(userId,notIn);
    }

    @Override
    public int countProjectTasks(HierarchicalId projectId, TaskState... notIn) {
        return repo.countProjectTasks(projectId.getUserId(),projectId.toString(),notIn);
    }


}
