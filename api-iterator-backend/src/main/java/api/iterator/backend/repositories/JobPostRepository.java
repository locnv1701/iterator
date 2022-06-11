package api.iterator.backend.repositories;

import api.iterator.backend.entities.JobPostEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends PagingAndSortingRepository<JobPostEntity, Long> {
    Page<JobPostEntity> findAll(Example<JobPostEntity> example, Pageable pageable);

    List<JobPostEntity> findByUserId(Long userId);

    @Query("SELECT companyId FROM JobPostEntity WHERE id= :jobPostId")
    long findCompanyIdByJobPostId(Long jobPostId);
}
