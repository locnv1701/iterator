
package api.iterator.backend.repositories;

import api.iterator.backend.entities.UserMatchRequestEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMatchRequestRepository extends PagingAndSortingRepository<UserMatchRequestEntity, Long> {
    List<UserMatchRequestEntity> findBySourceUserIdAndMatchStatus(Long sourceUserId, String matchStatus);

    List<UserMatchRequestEntity> findByTargetUserIdAndMatchStatus(Long targetUserId, String matchStatus);

    UserMatchRequestEntity findBySourceUserIdAndTargetUserIdAndJobPostId(Long sourceUserId, Long targetUserId, Long jobPostId);

    Page<UserMatchRequestEntity> findBySourceUserIdOrTargetUserId(Long sourceUserId, Long targetUserId, Pageable pageable);

    Page<UserMatchRequestEntity> findAll(Example<UserMatchRequestEntity> example, Pageable pageable);
}
