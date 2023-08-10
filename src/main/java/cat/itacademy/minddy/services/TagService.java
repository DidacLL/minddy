package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.NoteDTO;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.utils.MinddyException;

import java.util.List;
import java.util.UUID;

public interface TagService {

    TagDTO getTag(String userId, String tagName) throws MinddyException;

    Tag getTagEntity(String userId, String tagName) throws MinddyException;

    List<TagDTO> getProjectTags(HierarchicalId projectId, boolean onlyVisible);
    List<TagDTO> getNoteTags(String userId,String noteId,boolean onlyVisible);
    List<TagDTO> getTaskTags(String userId,String noteId,boolean onlyVisible);
    List<String> getTagsLike(String userId, String nameLike);
    int countTagUses(String userId,String tagName);

    Tag createTag(String userId, TagDTO dto) throws MinddyException;


    List<Tag> getNoteTags(String userId, UUID noteId);

    List<Tag> getTagsFromList(String userId, String... tagDTOS) throws MinddyException;

    Tag createTag(String userId, String name) throws MinddyException;

    List<Tag> getTagsFromList(String userId, TagDTO... tagDTOS) throws MinddyException;

    NoteDTO loadTags(String userId,NoteDTO noteDTO) throws MinddyException;
}
