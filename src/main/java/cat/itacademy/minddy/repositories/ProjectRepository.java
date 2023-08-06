package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.ProjectState;
import cat.itacademy.minddy.data.dao.Project;
import cat.itacademy.minddy.data.dto.views.ProjectMinimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
        p.uiConfig
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

}
