package api.iterator.backend.business.validators;

import api.iterator.backend.exceptions.MatchRequestExistException;
import api.iterator.backend.exceptions.MatchRequestNotExistException;
import api.iterator.backend.repositories.UserMatchRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {
    private final UserMatchRequestRepository userMatchRequestRepository;

    public RequestValidator(UserMatchRequestRepository userMatchRequestRepository) {
        this.userMatchRequestRepository = userMatchRequestRepository;
    }

    public void checkRequestExist(Long sourceUserId, Long targetUserId, Long jobPostId) throws MatchRequestExistException {
        if (userMatchRequestRepository.findBySourceUserIdAndTargetUserIdAndJobPostId(sourceUserId, targetUserId, jobPostId) != null) {
            throw new MatchRequestExistException();
        }
    }

    public void checkRequestNotExist(Long sourceUserId, Long targetUserId, Long jobPostId) throws MatchRequestNotExistException {
        if (userMatchRequestRepository.findBySourceUserIdAndTargetUserIdAndJobPostId(sourceUserId, targetUserId, jobPostId) == null) {
            throw new MatchRequestNotExistException();
        }
    }
}
