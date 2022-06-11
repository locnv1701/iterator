package api.iterator.backend.repositories;

import api.iterator.backend.entities.ApplicantExperienceEntity;
import api.iterator.backend.models.displays.applicant.ApplicantExperienceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantExperienceRepository extends JpaRepository<ApplicantExperienceEntity, Long> {
    @Query("SELECT new api.iterator.backend.models.displays.applicant.ApplicantExperienceModel(jobTitle, companyName, startDate, endDate, projectSelfDescription) FROM ApplicantExperienceEntity WHERE applicantId = :applicantId")
    List<ApplicantExperienceModel> getApplicantExperiences(Long applicantId);

    @Query("SELECT id FROM ApplicantExperienceEntity WHERE applicantId = :applicantId")
    List<Long> findAllApplicantExperienceIds(Long applicantId);

    void deleteByIdIn(List<Long> experienceIds);
}
