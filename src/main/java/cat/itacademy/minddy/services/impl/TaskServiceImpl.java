package cat.itacademy.minddy.services.impl;

import cat.itacademy.minddy.data.config.DefaultTags;
import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.TaskDTO;
import cat.itacademy.minddy.data.dto.views.TaskData;
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
//        System.out.println(date1);
        return repo.getTodayTasksNative(userID, date, PageRequest.of(page, pageSize)).map(TaskMinimal::fromTuple);
    }

    @Override
    public Page<TaskData> getProjectExpandedTasks(HierarchicalId projectId, int pageSize, int page, TaskState... notIn) {
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
    public Page<TaskData> getProjectExpandedTasksExclusive(HierarchicalId projectId, int pageSize, int page, TaskState... notIn) {
        if (notIn == null || notIn.length == 0) return repo.getProjectExpandedTasksExclusive(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize));
        if (notIn.length == 1) return repo.getProjectExpandedTasksExclusive(projectId.getUserId(),
                projectId.toString(),
                PageRequest.of(page, pageSize),
                notIn[0]);
        return repo.getProjectExpandedTasksExclusive(projectId.getUserId(),
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
    public TaskData getExpandedTask(String userId, UUID taskId) throws MinddyException {
        return repo.getExpandedTask(userId, taskId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found"));
    }

    @Override
    public TaskDTO getTask(String userId, UUID taskId) throws MinddyException {
        return TaskDTO.fromEntity(repo.findByIdAndHolderIdUserId( taskId,userId).orElseThrow(() -> new MinddyException(404, "task " + taskId + " not found")));
    }

    @Override
    public TaskDTO updateTask(HierarchicalId holderID, TaskDTO dto, TagDTO... tags) throws MinddyException {
        System.out.println(dto.getName());
        System.out.println(dto.getId());
        System.out.println(holderID.getUserId());
        System.out.println(holderID);
        var old = repo.findByIdAndHolderIdUserId(dto.getId(),holderID.getUserId()).orElseThrow(() -> new MinddyException(404, "task " + holderID +"/"+dto.getId()+ " not found (TaskService.125)"));
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
            if (dto.getDate().isAfter(old.getDate()))
                oldTags.add(Tag.fromDTO(tagService.getTag(holderID.getUserId(), DefaultTags.DELAYED.getTagName())));
            old.setDate(dto.getDate());
        }

        List<Tag> tagList = oldTags.stream().toList();
        System.out.println("tag list: " + tagList.size());
//        Task entity = old.setTags(tagList);
        Task save = repo.save(old);
        return TaskDTO.fromEntity(save);
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

    @Override
    public List<TaskMinimal> getMissedTasks(String userId, LocalDate today) {
        var taskList = repo.getMissedTasks(userId, today);
        var res = new ArrayList<TaskMinimal>();
        for (var t : taskList) {

            if(TaskState.parse(t.getState()) != TaskState.DEFERRED) {
                try {
                    updateTask(new HierarchicalId(userId,t.getHolder()), new TaskDTO().setState(TaskState.DEFERRED));
                } catch (MinddyException e) {
                    throw new RuntimeException(e);
                }
            }
            res.add(new TaskMinimal(t.getId()));
        }
        return res;
    }
    @Override
    public String getTaskHolder(String userId, String taskId) throws MinddyException {
        ProjectDTO project= ProjectDTO.fromEntity(repo.getTaskHolder(userId, UUID.fromString(taskId)).orElseThrow(()->new MinddyException(404,"Holder of Task: " + taskId+" NOT FOUND")));
        return project.getId().toString();
    }


}
