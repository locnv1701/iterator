package api.iterator.backend.repositories;

import api.iterator.backend.entities.SkillSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillSetRepository extends JpaRepository<SkillSetEntity, Long> {
    @Query("SELECT id FROM SkillSetEntity WHERE skillName= :skillName")
    Optional<Long> findIdBySkillName(String skillName);
}
