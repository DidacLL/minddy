package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dao.Note;
import cat.itacademy.minddy.data.dto.views.NoteExpanded;
import cat.itacademy.minddy.data.dto.views.NoteMinimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Query(value = """
    SELECT  Note FROM Note n WHERE n.holder= :projectId AND n.id= :noteId
""")
    Optional<Note> getNote(HierarchicalId projectId, String noteId);
    @Query(
            value = """
            SELECT NEW cat.itacademy.minddy.data.dto.views.NoteExpanded(
                n.id,
                n.name,
                n.body,
                n.type,
                n.isVisible            
            )
            FROM Note n
            WHERE n.holder.id=:projectId AND n.name=:name AND 
            (SELECT COUNT(t) FROM n.tags t WHERE t.id.name IN :tags) = SIZE(:tags)
            """
    )
    List<NoteExpanded> searchByNameAndTags(HierarchicalId projectId, String name, String... tags);

    @Query(value = """
    SELECT NEW cat.itacademy.minddy.data.dto.views.NoteMinimal(n.id,n.name) FROM Note n
    WHERE n.holder=:projectId AND n.isVisible=:visible
""")
    Page<NoteMinimal> getNotesByHolder(HierarchicalId projectId, Pageable pageable, boolean visible);

    @Query(nativeQuery = true,value = """
    SELECT * FROM notes n WHERE n.user=:userId AND NOT n.is_visible 
    AND(
        ( n.parent_id=:parentId AND n.holder_id=:holderId)
        OR (n.parent_id LIKE CONCAT(:parentId,:holderId,'%'))) 
    AND (SELECT COUNT(*) FROM notes_tags nt WHERE nt.note_id=n.id AND nt.tags_name:='_TASK_')<1
    ORDER BY n.creation_date
""")
    List<Note> getSystemNotes(String userId,String parentId,String holderId );

    @Query(nativeQuery = true,value = """
    SELECT (nt.note_id,n.name ) FROM notes_tags nt NATURAL JOIN notes n 
    WHERE n.user=:userId AND nt.tags_name IN :tagNames AND n.is_visible AND n.type IN :types AND( 
        (n.parent_id=:parentId AND n.holder_id=:holderId)
        OR (n.parent_id LIKE CONCAT(:parentId,:holderId,'%'))
        
    ) ORDER BY n.update_date DESC 
""")
    Page<NoteMinimal> searchByTagAndType(String userId, String parentId, String holderId, Pageable pageRequest, String[] tagNames, int... types);

    @Query(nativeQuery = true,value = """
    SELECT (nt.note_id,n.name ) FROM notes_tags nt NATURAL JOIN notes n 
    WHERE n.user=:userId AND n.is_visible AND n.type IN :types AND( 
        (n.parent_id=:parentId AND n.holder_id=:holderId)
        OR (n.parent_id LIKE CONCAT(:parentId,:holderId,'%'))
        AND  n.name LIKE CONCAT('%',:name,'%')
    ) ORDER BY n.update_date DESC 
""")
    Page<NoteMinimal> searchByNameAndType(String userId, String parentId, String holderId, Pageable pageable, String name, int... types);

    @Query(nativeQuery = true,value = """
    SELECT (nt.note_id,n.name ) FROM notes_tags nt NATURAL JOIN notes n 
    WHERE n.user=:userId AND n.is_visible AND n.type IN :types AND( 
        (n.parent_id=:parentId AND n.holder_id=:holderId)
        OR (n.parent_id LIKE CONCAT(:parentId,:holderId,'%'))
        AND n.body LIKE CONCAT('%',:text,'%')
    ) ORDER BY n.update_date DESC 
""")
    Page<NoteMinimal> searchByBodyAndType(String userId, String parentId, String holderId,Pageable pageable, String text, int... types);
}
