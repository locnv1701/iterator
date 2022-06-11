package api.iterator.backend.business.logic;

import api.iterator.backend.constants.MatchStatusConstant;
import api.iterator.backend.entities.UserMatchRequestEntity;
import api.iterator.backend.exceptions.MatchRequestNotExistException;
import api.iterator.backend.models.displays.applicant.UserDisplayModel;
import api.iterator.backend.models.displays.applicant.UserProfileDisplayModel;
import api.iterator.backend.models.displays.request.IncomingRequestModel;
import api.iterator.backend.repositories.JobPostRepository;
import api.iterator.backend.repositories.UserMatchRequestRepository;
import api.iterator.backend.repositories.UserProfileRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomingRequestBusinessImpl {
    private static final String PENDING = MatchStatusConstant.PENDING;

    private final UserMatchRequestRepository userMatchRequestRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final JobPostRepository jobPostRepository;

    public IncomingRequestBusinessImpl(UserMatchRequestRepository userMatchRequestRepository, UserRepository userRepository, UserProfileRepository userProfileRepository, JobPostRepository jobPostRepository) {
        this.userMatchRequestRepository = userMatchRequestRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.jobPostRepository = jobPostRepository;
    }

    public List<IncomingRequestModel> getIncomingRequests(Long userId) {
        List<UserMatchRequestEntity> pendingIncomingRequests = userMatchRequestRepository.findByTargetUserIdAndMatchStatus(userId, PENDING);
        if (pendingIncomingRequests == null) {
            return null;
        }

        List<IncomingRequestModel> incomingRequests = new ArrayList<>();
        pendingIncomingRequests.forEach(incomingRequest -> {
            Long sourceUserId = incomingRequest.getSourceUserId();

            UserDisplayModel userDisplay = userRepository.getUserDisplay(sourceUserId);
            UserProfileDisplayModel userProfileDisplayModel = userProfileRepository.getUserProfileDisplay(sourceUserId);

            // Check if user profile is edited => else return
            if (userProfileDisplayModel == null) {
                return;
            }

            IncomingRequestModel incomingRequestModel = new IncomingRequestModel();
            incomingRequestModel.setSourceUserId(sourceUserId);
            incomingRequestModel.setFirstName(userDisplay.getFirstName());
            incomingRequestModel.setLastName(userDisplay.getLastName());
            incomingRequestModel.setJobPostId(incomingRequest.getJobPostId());
            incomingRequestModel.setRequestMessage(incomingRequest.getRequestMessage());

            jobPostRepository.findById(incomingRequest.getJobPostId())
                    .ifPresent(jobPost -> incomingRequestModel.setJobName(jobPost.getJobType()));

            incomingRequests.add(incomingRequestModel);

        });
        return incomingRequests;
    }

    @Transactional
    public void setStatusForRequest(long userTargetId, long sourceUserId, long jobPostId, IncomingRequestModel incomingRequestModel) throws MatchRequestNotExistException {
        // Check if user match request exists
        UserMatchRequestEntity userMatchRequestEntity = userMatchRequestRepository.findBySourceUserIdAndTargetUserIdAndJobPostId(sourceUserId, userTargetId, jobPostId);
        if (userMatchRequestEntity == null) {
            throw new MatchRequestNotExistException();
        }

        userMatchRequestEntity.setMatchStatus(incomingRequestModel.getMatchStatus());
        userMatchRequestRepository.save(userMatchRequestEntity);
    }
}
