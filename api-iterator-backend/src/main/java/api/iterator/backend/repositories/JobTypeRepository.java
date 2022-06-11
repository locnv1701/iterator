package api.iterator.backend.repositories;

import api.iterator.backend.entities.JobTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTypeRepository extends JpaRepository<JobTypeEntity, Long> {
    @Query("SELECT id FROM JobTypeEntity WHERE jobType = :jobType")
    Optional<Long> findIdByJobType(String jobType);
}