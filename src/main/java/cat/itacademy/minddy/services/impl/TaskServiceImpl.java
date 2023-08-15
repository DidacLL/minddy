package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.DefaultTags;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TaskExpanded;
import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import cat.itacademy.minddy.repositories.TaskRepository;
import cat.itacademy.minddy.services.TagService;
import cat.itacademy.minddy.services.TaskService;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository repo;
    @Autowired
    private TagService tagService;

    @Override
    public Page<TaskMinimal> getProjectPendingMinTasks(HierarchicalId projectId, int pageSize, int page) {
        return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                TaskState.DONE,
                TaskState.DISCARDED
        );
    }

    @Override
    public Page<TaskMinimal> getAllProjectMinTasks(HierarchicalId projectId, TaskState... notIn) {
        if (notIn == null || notIn.length == 0) return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(0, Integer.MAX_VALUE));
        if (notIn.length == 1) return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(0, Integer.MAX_VALUE), notIn[0]);
        return repo.getProjectMinimalTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(0, Integer.MAX_VALUE), notIn);

    }

    @Override
    public Page<TaskMinimal> getTodayTasks(String userID, LocalDate date, int pageSize, int page) {
        Date date1 = Date.valueOf(date);
        System.out.println(date1);
        return repo.getTodayTasksNative(userID, date, PageRequest.of(page, pageSize)).map(TaskMinimal::fromTuple);
    }

    @Override
    public Page<TaskExpanded> getProjectExpandedTasks(HierarchicalId projectId, int pageSize, int page, TaskState... notIn) {
        if (notIn == null || notIn.length == 0) return repo.getProjectExpandedTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize));
        if (notIn.length == 1) return repo.getProjectExpandedTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                notIn[0]);
        return repo.getProjectExpandedTasks(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                notIn);
    }

    @Override
    public TaskDTO createNewTask(Project project, TaskDTO dto, TagDTO... tags) throws MinddyException {
        try {
            return TaskDTO.fromEntity(
                    repo.save(Task.fromDTO(dto)
                            .setHolder(project)
                            .setTags(tags.length > 0 ? Arrays.stream(tags).map((x) -> Tag.fromDTO(x, project.getId().getUserId())).toList() : new ArrayList<>())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //FIXME
            throw new MinddyException(400, "Project holder not found");
        }
    }

    @Override
    public TaskMinimal getMinimalTask(String userId, UUID taskId) throws MinddyException {
        return repo.getMinimalTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found"));
    }

    @Override
    public TaskExpanded getExpandedTask(String userId, UUID taskId) throws MinddyException {
        return repo.getExpandedTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found"));
    }

    @Override
    public TaskDTO getTask(String userId, UUID taskId) throws MinddyException {
        return TaskDTO.fromEntity(repo.getTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found")));
    }

    @Override
    public TaskDTO updateTask(HierarchicalId holderID, TaskDTO dto, TagDTO... tags) throws MinddyException {
        var old = repo.getTask(holderID.getUserId(), dto.getId()).orElseThrow(() -> new MinddyException(404, "task " + holderID + " not found"));
        Set<Tag> oldTags = new HashSet<>(tagService.getTaskTags(holderID.getUserId(), old.getId(), true).stream().map((x) -> Tag.fromDTO(x, holderID.getUserId())).toList());
        oldTags.addAll(tagService.getTaskTags(holderID.getUserId(), old.getId(), false).stream().map((x) -> Tag.fromDTO(x, holderID.getUserId())).toList());
        //CHECK NAME
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) old.setName(dto.getName());
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty())
            old.setDescription(dto.getDescription());
        if (dto.getState() != null && !dto.getState().equals(old.getState())) old.setState(dto.getState());
        if (dto.getPriority() != null && !dto.getPriority().equals(old.getPriority()))
            old.setPriority(dto.getPriority());
        //CHECK TAGS
        if (tags != null && tags.length > 0)
            oldTags.addAll(Arrays.stream(tags).map((x) -> Tag.fromDTO(x, holderID.getUserId())).toList());
        //CHECK Date
        if (dto.getDate() != null && !dto.getDate().equals(old.getDate())) {
            if (dto.getDate().isAfter(old.getDate())) oldTags.add(Tag.fromDTO(tagService.getTag(holderID.getUserId(), DefaultTags.DELAYED.getTagName())));
            old.setDate(dto.getDate());
        }
        return TaskDTO.fromEntity(repo.save(old.setTags(oldTags.stream().toList())));
    }

    @Override
    public void deleteTask(HierarchicalId holderId, UUID id) throws MinddyException {
        repo.delete(
                repo.getTask(holderId.getUserId(), id)
                        .orElseThrow(() -> new MinddyException(404, "task " + holderId + " not found"))
        );
    }

    @Override
    public int countUserTasks(String userId, TaskState... notIn) {
        if (notIn == null || notIn.length == 0) return repo.countUserTasks(userId);
        if (notIn.length == 1) return repo.countUserTasks(userId, notIn[0].ordinal());
        return repo.countUserTasks(userId, Arrays.stream(notIn)
                .mapToInt(Enum::ordinal)
                .toArray());
    }

    @Override
    public int countProjectTasks(HierarchicalId projectId, TaskState... notIn) {
        if (notIn == null || notIn.length == 0)
            return repo.countProjectTasks(projectId.getUserId(), projectId.toString());
        if (notIn.length == 1) return repo.countProjectTasks(projectId.getUserId(), projectId.toString(), notIn[0]);
        return repo.countProjectTasks(projectId.getUserId(), projectId.toString(), notIn);
    }


}
