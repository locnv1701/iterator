package api.iterator.backend.repositories;

import api.iterator.backend.entities.ApplicantEducationEntity;
import api.iterator.backend.models.displays.applicant.ApplicantEducationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantEducationRepository extends JpaRepository<ApplicantEducationEntity, Long> {
    @Query("SELECT new api.iterator.backend.models.displays.applicant.ApplicantEducationModel(major, collegeName, startingDate, completionDate, cgpa, degreeName) from ApplicantEducationEntity where applicantId = :applicantId")
    Optional<ApplicantEducationModel> getApplicantEducation(Long applicantId);

    Optional<ApplicantEducationEntity> findByApplicantId(Long applicantId);
}
