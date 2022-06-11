package api.iterator.backend.repositories;

import api.iterator.backend.entities.ApplicantEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends PagingAndSortingRepository<ApplicantEntity, Long> {
    Page<ApplicantEntity> findAll(Example<ApplicantEntity> example, Pageable pageable);

    Optional<ApplicantEntity> findByUserId(Long userId);
}
