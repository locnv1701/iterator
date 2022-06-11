package api.iterator.backend.business.logic;

import api.iterator.backend.constants.UserTypeConstant;
import api.iterator.backend.entities.UserEntity;
import api.iterator.backend.entities.UserProfileEntity;
import api.iterator.backend.exceptions.UserNotExistException;
import api.iterator.backend.models.UserProfileModel;
import api.iterator.backend.models.displays.UserProfilePageModel;
import api.iterator.backend.repositories.ApplicantRepository;
import api.iterator.backend.repositories.UserProfileRepository;
import api.iterator.backend.repositories.UserRepository;
import api.iterator.backend.utils.ReflectUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserBusinessImpl {
    private static final String APPLICANT = UserTypeConstant.APPLICANT;

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ApplicantRepository applicantRepository;

    public UserBusinessImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, ApplicantRepository applicantRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.applicantRepository = applicantRepository;
    }

    @Transactional
    public void editProfile(UserProfileModel userProfileModel, Long userId) {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserId(userId);
        if (userProfileEntity == null) {
            userProfileEntity = new UserProfileEntity(userId);

            // Validate not null fields when creating new user profile
//            Validate.notBlank(userProfileModel.getProfileDisplayName(), "Field profile display name must not be blank.");
//            Validate.notNull(userProfileModel.getAge(), "Field age must not be null.");
//            Validate.notBlank(userProfileModel.getGender(), "Field gender must not be blank.");
            Validate.notBlank(userProfileModel.getLocation(), "Field location must not be blank.");
            Validate.notBlank(userProfileModel.getStatus(), "Field status must not be blank.");
        }

        // Set not null properties for user profile entity
        ReflectUtils.setNotNullProperties(userProfileModel, userProfileEntity);

        userProfileRepository.save(userProfileEntity);
    }

    public UserProfilePageModel displayProfilePage(Long userId) throws UserNotExistException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId));

        // Query for user profile, or else only get user register info
        UserProfilePageModel userProfilePageModel = userProfileRepository.getUserProfileModel(userId).orElse(new UserProfilePageModel());
        userProfilePageModel.setFirstName(userEntity.getFirstName());
        userProfilePageModel.setLastName(userEntity.getLastName());
        userProfilePageModel.setPhoneNumber(userEntity.getPhoneNumber());
        userProfilePageModel.setEmail(userEntity.getEmail());

        // Check if user role is Applicant and set register applicant info
        if (userEntity.getUserType().equals(APPLICANT)) {
            applicantRepository.findByUserId(userId).ifPresent(applicant -> {
                userProfilePageModel.setEntryLevel(applicant.getEntryLevel());
                userProfilePageModel.setExperienceYear(applicant.getExperienceYear());
            });
        }

        return userProfilePageModel;
    }
}
