package api.iterator.backend.repositories;

import api.iterator.backend.entities.UserProfileEntity;
import api.iterator.backend.models.displays.applicant.UserProfileDisplayModel;
import api.iterator.backend.models.displays.UserProfilePageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    UserProfileEntity findByUserId(Long userId);

    @Query("SELECT new api.iterator.backend.models.displays.applicant.UserProfileDisplayModel(age, gender, location, status) FROM UserProfileEntity WHERE userId = :userId")
    UserProfileDisplayModel getUserProfileDisplay(Long userId);

    @Query("SELECT new api.iterator.backend.models.displays.UserProfilePageModel(profileDisplayName, age, gender, location, status, githubLink, facebookLink, instagramLink, twitterLink)" +
            " FROM UserProfileEntity WHERE userId = :userId")
    Optional<UserProfilePageModel> getUserProfileModel(Long userId);
}
