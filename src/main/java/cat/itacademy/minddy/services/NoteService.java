package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.config.NoteType;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.data.dto.views.NoteMinimal;
import cat.itacademy.minddy.data.dto.views.NoteRequest;
import cat.itacademy.minddy.utils.MinddyException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteDTO createNewNote(HierarchicalId projectId, NoteDTO note, TagDTO ... tags) throws MinddyException;
    NoteDTO createTaskNote(HierarchicalId projectId,String taskId, NoteDTO note,TagDTO ... tags) throws MinddyException;
    NoteDTO updateNote(HierarchicalId projectId, NoteDTO note, TagDTO...tags) throws MinddyException;
    NoteDTO deleteNote(HierarchicalId projectId,UUID noteId) throws MinddyException;

    Page<NoteMinimal> getAllVisibleNotes(HierarchicalId projectId,int page,int pageSize) throws MinddyException;
    List<NoteDTO> getSystemNotes(HierarchicalId projectId) throws MinddyException;
    List<NoteMinimal> getTaskNotes(HierarchicalId projectId, String taskId) throws MinddyException;

    NoteDTO getNote(HierarchicalId projectId, UUID noteId) throws MinddyException;
    NoteRequest getFullNote(HierarchicalId projectId, UUID noteId) throws MinddyException;
    NoteRequest getFullNote(String user, UUID noteId) throws MinddyException;

    Page<NoteMinimal> searchNotesByTag(HierarchicalId projectId, int page, int pageSize, String[] tagNames, NoteType... types)throws MinddyException;
    Page<NoteMinimal> searchNotesByName(HierarchicalId projectId,int page,int pageSize,String name, NoteType... types)throws MinddyException;
    Page<NoteMinimal> searchNotesByContent(HierarchicalId projectId,int page,int pageSize,String text, NoteType... types)throws MinddyException;


    List<NoteMinimal> getProjectPinnedNotes(HierarchicalId hierarchicalId) throws MinddyException;
}
