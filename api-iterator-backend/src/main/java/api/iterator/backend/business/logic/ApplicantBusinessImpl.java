package api.iterator.backend.business.logic;

import api.iterator.backend.entities.ApplicantEducationEntity;
import api.iterator.backend.entities.ApplicantExperienceEntity;
import api.iterator.backend.models.displays.applicant.ApplicantEducationModel;
import api.iterator.backend.models.displays.applicant.ApplicantExperienceModel;
import api.iterator.backend.repositories.ApplicantEducationRepository;
import api.iterator.backend.repositories.ApplicantExperienceRepository;
import api.iterator.backend.entities.ApplicantSkillSetEntity;
import api.iterator.backend.entities.SkillSetEntity;
import api.iterator.backend.models.displays.applicant.SkillModel;
import api.iterator.backend.repositories.ApplicantSkillSetRepository;
import api.iterator.backend.repositories.SkillSetRepository;
import api.iterator.backend.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApplicantBusinessImpl {
    private final ApplicantEducationRepository applicantEducationRepository;
    private final ApplicantExperienceRepository applicantExperienceRepository;

    private final SkillSetRepository skillSetRepository;
    private final ApplicantSkillSetRepository applicantSkillSetRepository;

    public ApplicantBusinessImpl(ApplicantEducationRepository applicantEducationRepository, ApplicantExperienceRepository applicantExperienceRepository, SkillSetRepository skillSetRepository, ApplicantSkillSetRepository applicantSkillSetRepository) {
        this.applicantEducationRepository = applicantEducationRepository;
        this.applicantExperienceRepository = applicantExperienceRepository;
        this.skillSetRepository = skillSetRepository;
        this.applicantSkillSetRepository = applicantSkillSetRepository;
    }

    @Transactional
    public void editEducation(ApplicantEducationModel applicantEducationModel, Long applicantId) {
        ApplicantEducationEntity applicantEducationEntity = applicantEducationRepository.findByApplicantId(applicantId).orElseGet(() -> {
            Validate.isTrue(StringUtils.isNotBlank(applicantEducationModel.getMajor()), "Field major must not be blank.");
            Validate.isTrue(StringUtils.isNotBlank(applicantEducationModel.getCollegeName()), "Field college name must not be blank.");

            return new ApplicantEducationEntity(applicantId);
        });

        // Set not null properties for applicant education entity
        ReflectUtils.setNotNullProperties(applicantEducationModel, applicantEducationEntity);

        applicantEducationRepository.save(applicantEducationEntity);
    }

    @Transactional
    public void editExperiences(List<ApplicantExperienceModel> applicantExperienceModels, Long applicantId) {
        List<Long> experienceIds = applicantExperienceRepository.findAllApplicantExperienceIds(applicantId);
        // delete old list experience
        applicantExperienceRepository.deleteByIdIn(experienceIds);

        // flushes all pending changes to the database
        applicantExperienceRepository.flush();

        // create new list experience
        applicantExperienceModels.forEach(applicantExperienceModel -> {
            ApplicantExperienceEntity applicantExperienceEntity = new ApplicantExperienceEntity(applicantId);
            ReflectUtils.setNotNullProperties(applicantExperienceModel, applicantExperienceEntity);

            applicantExperienceRepository.save(applicantExperienceEntity);
        });
    }

    @Transactional
    public void editSkillSets(List<SkillModel> skillModels, long applicantId) {
        List<Long> skillSetIds = applicantSkillSetRepository.findAllApplicantSkillSetIds(applicantId);
        // delete old list experience
        applicantSkillSetRepository.deleteByIdIn(skillSetIds);

        // flushes all pending changes to the database
        applicantSkillSetRepository.flush();

        Set<String> skillModelSet = new HashSet<>();
        // Filter unique skill models by name
        List<SkillModel> uniqueSkillModels = skillModels
                .stream()
                .filter(e -> skillModelSet.add(e.getSkillName()))
                .collect(Collectors.toList());

        // create new list experience
        uniqueSkillModels.forEach(skillModel -> {
            String skillName = skillModel.getSkillName();
            ApplicantSkillSetEntity applicantSkillSetEntity = new ApplicantSkillSetEntity(applicantId);

            // get skill id from skill name, if skill name does not exist => create new and get its id
            Long skillId = skillSetRepository.findIdBySkillName(skillName).orElseGet(() -> {
                SkillSetEntity newSkillSetEntity = skillSetRepository.save(new SkillSetEntity(skillName));

                return newSkillSetEntity.getId();
            });

            applicantSkillSetEntity.setSkillId(skillId);
            applicantSkillSetEntity.setLevel(skillModel.getLevel());

            applicantSkillSetRepository.save(applicantSkillSetEntity);
        });
    }
}
