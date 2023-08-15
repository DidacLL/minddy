package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.TaskState;
import cat.itacademy.minddy.data.dao.Task;
import cat.itacademy.minddy.data.dto.views.TaskExpanded;
import cat.itacademy.minddy.data.dto.views.TaskMinimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, HierarchicalId> {
    @Query(value = """
                    SELECT NEW cat.itacademy.minddy.data.dto.views.TaskMinimal(
                        t.id,
                        t.date,
                        CONCAT(t.holder.id.holderId,t.holder.id.ownId),
                        t.name,
                        t.holder.name
                    )
                    FROM Task t
                    WHERE t.holder.id.userId= :userId
                     AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                     AND t.state NOT IN :notIn
                     ORDER BY t.date ASC,t.priority DESC
            """)
    Page<TaskMinimal> getProjectMinimalTasks(String userId, String projectId, Pageable pageable, TaskState... notIn);
    @Query(value = """
                    SELECT NEW cat.itacademy.minddy.data.dto.views.TaskMinimal(
                        t.id,
                        t.date,
                        CONCAT(t.holder.id.holderId,t.holder.id.ownId),
                        t.name,
                        t.holder.name
                    )
                    FROM Task t
                    WHERE t.holder.id.userId= :userId
                     AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                     AND t.state <> :notIn
                     ORDER BY t.date ASC,t.priority DESC
            """)
    Page<TaskMinimal> getProjectMinimalTasks(String userId, String projectId, Pageable pageable, TaskState notIn);
    @Query(value = """
                    SELECT NEW cat.itacademy.minddy.data.dto.views.TaskMinimal(
                        t.id,
                        t.date,
                        CONCAT(t.holder.id.holderId,t.holder.id.ownId),
                        t.name,
                        t.holder.name
                    )
                    FROM Task t
                    WHERE t.holder.id.userId= :userId
                     AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                     ORDER BY t.date ASC,t.priority DESC
            """)
//                     ORDER BY t.date ASC,t.priority DESC
    Page<TaskMinimal> getProjectMinimalTasks(String userId, String projectId, Pageable pageable);
    @Query(value = """
                SELECT
                t.id , t.date , t.name , CONCAT(p.holder_id,p.own_id) ,p.name
                FROM tasks t
                JOIN projects p ON t.parent_id = p.holder_id AND t.holder_id = p.own_id AND t.user = p.user_id
                WHERE t.user = :userId
                AND t.state<=3 AND t.date <= ADDDATE(:date, INTERVAL t.priority DAY)
            """, nativeQuery = true,name = "getTodayTasksNative")
    Page<Object[]> getTodayTasksNative(String userId, LocalDate date, Pageable pageable);
//
//    @Query(value = """
//                        SELECT NEW cat.itacademy.minddy.data.dto.views.TaskMinimal(
//                        t.id,
//                        t.date,
//                        CONCAT(t.holder.id.holderId,t.holder.id.ownId),
//                        t.name,
//                        t.holder.name
//                    )
//                    FROM Task t
//                    WHERE t.holder.id.userId= :userId
//                    AND t.state NOT IN (
//                        cat.itacademy.minddy.data.config.TaskState.DISCARDED,
//                        cat.itacademy.minddy.data.config.TaskState.DONE
//                        )
//                    AND t.date <=FUNCTION('DATE_ADD',:date, INTERVAL 1 DAY)
//            """)
////                     (
////                    CASE t.priority
////                    WHEN cat.itacademy.minddy.data.config.Priority.LOW THEN adddate(:date,1)
////                    WHEN cat.itacademy.minddy.data.config.Priority.NORMAL THEN  adddate(:date,2)
////                    WHEN cat.itacademy.minddy.data.config.Priority.HIGH THEN  adddate(:date,3)
////                    WHEN cat.itacademy.minddy.data.config.Priority.HIGHER THEN  adddate(:date,4)
////                    ELSE :date
////                    END
////                    )
//    Page<TaskMinimal> getTodayTasks(String userId, Date date, Pageable pageable);

    @Query(value = """
            SELECT NEW cat.itacademy.minddy.data.dto.views.TaskExpanded(
                t.id,
                t.name,
                t.description,
                t.date,
                CONCAT( t.holder.id.holderId,t.holder.id.ownId),
                t.state,
                t.priority,
                t.holder.name
            )
            FROM Task t
            WHERE t.holder.id.userId= :userId
                 AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                 OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                 AND t.state NOT IN :notIn
                 ORDER BY t.date ASC,t.priority DESC""")
    Page<TaskExpanded> getProjectExpandedTasks(String userId, String projectId, Pageable pageable, TaskState... notIn);

    @Query(value = """
            SELECT NEW cat.itacademy.minddy.data.dto.views.TaskExpanded(
                t.id,
                t.name,
                t.description,
                t.date,
                CONCAT( t.holder.id.holderId,t.holder.id.ownId),
                t.state,
                t.priority,
                t.holder.name
            )
            FROM Task t
            WHERE t.holder.id.userId= :userId
                 AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                 OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                 AND t.state <> :notIn
                 ORDER BY t.date ASC,t.priority DESC""")
    Page<TaskExpanded> getProjectExpandedTasks(String userId, String projectId, Pageable pageable, TaskState notIn);

    @Query(value = """
            SELECT NEW cat.itacademy.minddy.data.dto.views.TaskExpanded(
                t.id,
                t.name,
                t.description,
                t.date,
                CONCAT( t.holder.id.holderId,t.holder.id.ownId),
                t.state,
                t.priority,
                t.holder.name
            )
            FROM Task t
            WHERE t.holder.id.userId= :userId
                 AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                 OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                 ORDER BY t.date ASC,t.priority DESC""")
    Page<TaskExpanded> getProjectExpandedTasks(String userId, String projectId, Pageable pageable);

    @Query(value = """
                SELECT NEW cat.itacademy.minddy.data.dto.views.TaskMinimal(t.id,t.date,CONCAT(t.holder.id.holderId, t.holder.id.ownId),t.name,t.holder.name)
                    FROM Task t
                    WHERE t.holder.id.userId = :userId AND t.id=:taskId
            """)
    Optional<TaskMinimal> getMinimalTask(String userId, UUID taskId);

    @Query( value = """
                SELECT NEW cat.itacademy.minddy.data.dto.views.TaskExpanded(t.id,
                    t.name,
                    t.description,
                    t.date,
                    CONCAT( t.holder.id.holderId,t.holder.id.ownId),
                    t.state,
                    t.priority,
                    t.holder.name)
                    FROM Task t
                    WHERE t.holder.id.userId = :userId AND t.id=:taskId
            """)
    Optional<TaskExpanded> getExpandedTask(String userId, UUID taskId);

    @Query(value = """
                SELECT t
                FROM Task t
                WHERE t.holder.id.userId= :userId AND t.id=:taskId
            """)
    Optional<Task> getTask(String userId, UUID taskId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) FROM tasks t WHERE user=:userId AND t.state NOT IN :notIn""")
    int countUserTasks(String userId, int[] notIn);
    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) FROM tasks t WHERE user=:userId AND t.state <> :notIn""")
    int countUserTasks(String userId, int notIn);
    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) FROM tasks t WHERE user=:userId""")
    int countUserTasks(String userId);

    @Query( value = """
                SELECT COUNT(t) FROM Task t
                WHERE  t.holder.id.userId= :userId
                AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                     AND t.state <> :notIn
            """)
    int countProjectTasks(String userId, String projectId, TaskState notIn);

    @Query( value = """
                SELECT COUNT(t) FROM Task t
                WHERE  t.holder.id.userId= :userId
                AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
            """)
    int countProjectTasks(String userId, String projectId);
    @Query(value = """
                SELECT COUNT(t) FROM Task t
                WHERE  t.holder.id.userId= :userId
                AND ( t.holder.id.holderId LIKE CONCAT(:projectId, '%')
                     OR CONCAT(t.holder.id.holderId,t.holder.id.ownId)= :projectId)
                     AND t.state NOT IN :notIn
            """)
    int countProjectTasks(String userId, String projectId, TaskState... notIn);

}
