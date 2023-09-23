package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.ProjectDTO;
import cat.itacademy.minddy.data.dto.views.ProjectMinimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, HierarchicalId> {
    @Query(nativeQuery = true, value = """
            SELECT p.own_id FROM minddy_test.projects p
            WHERE p.user_id=:user AND p.holder_id = :parentID
            ORDER BY p.creation_date DESC
            LIMIT 1
            """)
    Optional<String> getLastSubprojectsID( String user,String parentID);

    @Query("""
    SELECT NEW cat.itacademy.minddy.data.dto.views.ProjectMinimal(
        p.id.holderId,
        p.id.ownId,
        p.name,
        COUNT(n),
        COUNT(t),
        p.uiConfig,
        p.state
    )
    FROM Project p
    LEFT JOIN p.notes n
    LEFT JOIN p.tasks t
        ON t.state NOT IN (
            cat.itacademy.minddy.data.config.TaskState.DISCARDED,
            cat.itacademy.minddy.data.config.TaskState.DONE
        )
    WHERE p.id.userId = :user AND p.state NOT IN :states
    GROUP BY p.id.holderId, p.id.ownId, p.name, p.uiConfig
""")
    List<ProjectMinimal> getAllProjectsMin(@Param("user") String user, @Param("states") ProjectState... notIn);

    @Query(value = """
    SELECT NEW cat.itacademy.minddy.data.dto.ProjectDTO(
        p.id,p.name,p.description,p.state,p.deadLine,p.uiConfig
    )FROM Project p WHERE p.id.userId= :userId AND
     p.id.holderId LIKE CONCAT(:holderId,:ownId,'%')
    ORDER BY CASE WHEN p.deadLine IS NULL THEN 0 ELSE 1 END, p.deadLine ASC
""")
    List<ProjectDTO> getAllSubProjects(String userId, String holderId, String ownId);

    @Query(value = """
    SELECT COUNT(*) FROM Project p WHERE p.id.userId = :userId AND p.id.holderId= :parentId
""")
    int countChildren(String userId, String parentId);

    @Query("""
    SELECT NEW cat.itacademy.minddy.data.dto.views.ProjectMinimal(
        p.id.holderId,
        p.id.ownId,
        p.name,
        COUNT(n),
        COUNT(t),
        p.uiConfig,
        p.state
    )
    FROM Project p
    LEFT JOIN p.notes n
    LEFT JOIN p.tasks t
    WHERE p.id.userId = :userId
    AND p.id.holderId=:parentId
    GROUP BY p
    ORDER BY p.deadLine ASC
    
""")
    List<ProjectMinimal> getImmediateSubprojects(String userId, String parentId);
    @Query("""
    SELECT NEW cat.itacademy.minddy.data.dto.views.ProjectMinimal(
        p.id.holderId,
        p.id.ownId,
        p.name,
        COUNT(n),
        COUNT(t),
        p.uiConfig,
        p.state
    )
    FROM Project p
    LEFT JOIN p.notes n
    LEFT JOIN p.tasks t
        ON t.state NOT IN (
            cat.itacademy.minddy.data.config.TaskState.DISCARDED,
            cat.itacademy.minddy.data.config.TaskState.DONE
        )
    WHERE p.id= :id
    GROUP BY p.id.holderId, p.id.ownId, p.name, p.uiConfig
""")
    Optional<ProjectMinimal> getProjectMin(HierarchicalId id );

    @Query(nativeQuery = true,value = """
    SELECT CONCAT(p.holder_id,p.own_id) FROM projects p WHERE p.user_id=:userId
                AND p.state<=4 AND p.dead_line <= ADDDATE(:today, INTERVAL 7-p.state DAY)
""")
    List<String > getProjectsByNearestDeadLine(String userId, LocalDate today);
}
