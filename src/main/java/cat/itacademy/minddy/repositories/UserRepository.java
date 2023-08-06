package cat.itacademy.minddy.repositories;

import cat.itacademy.minddy.data.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByNameIgnoreCase(String userName);
}
