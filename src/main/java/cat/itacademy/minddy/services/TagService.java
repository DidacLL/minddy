package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.HierarchicalId;
import cat.itacademy.minddy.data.dao.Tag;
import cat.itacademy.minddy.data.dto.TagDTO;
import cat.itacademy.minddy.utils.MinddyException;

import java.util.List;
import java.util.UUID;

public interface TagService {

    //Getters
    TagDTO getTag(String userId, String tagName) throws MinddyException;

    Tag getTagEntity(String userId, String tagName) throws MinddyException;

    List<TagDTO> getProjectTags(HierarchicalId projectId, boolean onlyVisible);
    List<Tag> getProjectTagsEntity(HierarchicalId projectId, boolean onlyVisible);
    List<TagDTO> getNoteTags(String userId, UUID noteId, boolean onlyVisible);
    List<Tag> getNoteTagsEntity(String userId, UUID noteId);
    List<TagDTO> getTaskTags(String userId, UUID taskId, boolean onlyVisible);
    List<Tag> getTaskTagsEntity(String userId, UUID taskId, boolean onlyVisible);
    List<String> searchTagsLike(String userId, String nameLike);
    //Count
    int countTagUses(String userId,String tagName);
    //Create
    TagDTO createTag(String userId, TagDTO dto) throws MinddyException;
    Tag createTag(String userId, String name) throws MinddyException;
    //Update
    TagDTO updateTag(String userId, String oldName, TagDTO newData) throws MinddyException;

    boolean deleteTag(String userId, TagDTO tag) throws MinddyException;

}
