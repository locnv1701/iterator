package api.iterator.backend.repositories;

import api.iterator.backend.entities.BusinessStreamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessStreamRepository extends JpaRepository<BusinessStreamEntity, Long> {
    @Query("SELECT id FROM BusinessStreamEntity WHERE businessStream = :businessStream")
    Optional<Long> findIdByBusinessStream(String businessStream);
}
