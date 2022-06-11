package api.iterator.backend.business.logic;

import api.iterator.backend.constants.UserStatusConstant;
import api.iterator.backend.entities.ApplicantEntity;
import api.iterator.backend.models.displays.applicant.*;
import api.iterator.backend.repositories.*;
import api.iterator.backend.utils.ReflectUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApplicantDisplayBusinessImpl {
    private static final String ACTIVE = UserStatusConstant.ACTIVE;
    private static final String INACTIVE = UserStatusConstant.INACTIVE;

    private final ApplicantRepository applicantRepository;
    private final ApplicantEducationRepository applicantEducationRepository;
    private final ApplicantExperienceRepository applicantExperienceRepository;
    private final ApplicantSkillSetRepository applicantSkillSetRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMatchRequestRepository userMatchRequestRepository;

    public ApplicantDisplayBusinessImpl(ApplicantRepository applicantRepository, ApplicantEducationRepository applicantEducationRepository, ApplicantExperienceRepository applicantExperienceRepository, ApplicantSkillSetRepository applicantSkillSetRepository, UserRepository userRepository, UserProfileRepository userProfileRepository, UserMatchRequestRepository userMatchRequestRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantEducationRepository = applicantEducationRepository;
        this.applicantExperienceRepository = applicantExperienceRepository;
        this.applicantSkillSetRepository = applicantSkillSetRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userMatchRequestRepository = userMatchRequestRepository;
    }

    public List<ApplicantDisplayModel> getApplicantsByFilter(ApplicantFilterModel applicantFilterModel, Integer page, Integer size, Long userId, Long jobPostId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ApplicantEntity> applicantPage;
        if (applicantFilterModel == null) { // Without filter, find all
            applicantPage = applicantRepository.findAll(pageRequest);
        } else {
            applicantPage = applicantRepository.findAll(getJpaExample(applicantFilterModel), pageRequest);
        }

        if (applicantPage == null) {
            return null;
        }

        List<ApplicantEntity> applicants = applicantPage.getContent();

        List<ApplicantDisplayModel> displayModels = new ArrayList<>();
        applicants.forEach(applicant ->
                {
                    UserDisplayModel userDisplayModel = userRepository.getUserDisplay(applicant.getUserId());
                    UserProfileDisplayModel userProfileDisplayModel = userProfileRepository.getUserProfileDisplay(applicant.getUserId());

                    // Check if user profile is edited and status is active => else return
                    if (userProfileDisplayModel == null || Objects.equals(userProfileDisplayModel.getStatus(), INACTIVE)) {
                        return;
                    }

                    // Check if user match request not exist => else return
                    if (userMatchRequestRepository.findBySourceUserIdAndTargetUserIdAndJobPostId(userId, applicant.getUserId(), jobPostId) != null)
                        return;

                    displayModels.add(getApplicantDisplayModel(
                            applicant,
                            userDisplayModel,
                            userProfileDisplayModel
                    ));
                }
        );

        return displayModels;
    }

    public ApplicantDisplayModel getApplicantDisplayModelByUserId(Long userId) {
        ApplicantEntity applicant = applicantRepository.findByUserId(userId).get();
        UserDisplayModel userDisplayModel = userRepository.getUserDisplay(userId);
        UserProfileDisplayModel userProfileDisplayModel = userProfileRepository.getUserProfileDisplay(userId);

        return getApplicantDisplayModel(
                applicant,
                userDisplayModel,
                userProfileDisplayModel
        );
    }

    private ApplicantDisplayModel getApplicantDisplayModel(
            ApplicantEntity applicant,
            UserDisplayModel userDisplayModel,
            UserProfileDisplayModel userProfileDisplayModel
    ) {
        ApplicantDisplayModel displayModel = new ApplicantDisplayModel();

        Long applicantId = applicant.getId();

        applicantEducationRepository.getApplicantEducation(applicantId).ifPresent(displayModel::setEducation);

        List<ApplicantExperienceModel> experienceModels = applicantExperienceRepository.getApplicantExperiences(applicantId);
        if (experienceModels != null) {
            displayModel.setExperiences(experienceModels);
        }

        List<SkillModel> skillSet = applicantSkillSetRepository.getSkillSet(applicantId);
        if (skillSet != null) {
            displayModel.setSkills(skillSet);
        }

        displayModel.setFirstName(userDisplayModel.getFirstName());
        displayModel.setLastName(userDisplayModel.getLastName());

        displayModel.setAge(userProfileDisplayModel.getAge());
        displayModel.setGender(userProfileDisplayModel.getGender());
        displayModel.setLocation(userProfileDisplayModel.getLocation());

        displayModel.setUserId(applicant.getUserId());
        displayModel.setEntryLevel(applicant.getEntryLevel());
        displayModel.setExperienceYear(applicant.getExperienceYear());

        return displayModel;
    }

    private Example<ApplicantEntity> getJpaExample(ApplicantFilterModel applicantFilterModel) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        ApplicantEntity applicant = new ApplicantEntity();

        // Set properties for example if filter not null
        if (applicantFilterModel != null) {
            ReflectUtils.setNotNullProperties(applicantFilterModel, applicant);
        }

        return Example.of(applicant, matcher);
    }
}
