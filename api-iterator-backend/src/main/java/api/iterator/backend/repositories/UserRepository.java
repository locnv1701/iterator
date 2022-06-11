package api.iterator.backend.repositories;

import api.iterator.backend.entities.UserEntity;
import api.iterator.backend.models.displays.applicant.UserDisplayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserById(Long userId);
    UserEntity findByEmail(String email);

    @Query("SELECT new api.iterator.backend.models.displays.applicant.UserDisplayModel(firstName, lastName) FROM UserEntity where id = :userId")
    UserDisplayModel getUserDisplay(Long userId);
}
