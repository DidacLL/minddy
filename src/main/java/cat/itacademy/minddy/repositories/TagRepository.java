package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.config.TagId;
import cat.itacademy.minddy.data.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, TagId> {
}
