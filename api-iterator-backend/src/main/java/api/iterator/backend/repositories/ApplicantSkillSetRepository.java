package api.iterator.backend.repositories;

import api.iterator.backend.entities.ApplicantSkillSetEntity;
import api.iterator.backend.models.displays.applicant.SkillModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantSkillSetRepository extends JpaRepository<ApplicantSkillSetEntity, Long> {
    @Query("SELECT new api.iterator.backend.models.displays.applicant.SkillModel(skill.skillName, level) FROM ApplicantSkillSetEntity where applicantId = :applicantId")
    List<SkillModel> getSkillSet(Long applicantId);

    @Query("SELECT id FROM ApplicantSkillSetEntity WHERE applicantId = :applicantId")
    List<Long> findAllApplicantSkillSetIds(long applicantId);

    void deleteByIdIn(List<Long> skillSetIds);
}
