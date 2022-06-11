package api.iterator.backend.business.logic;

import api.iterator.backend.constants.MatchStatusConstant;
import api.iterator.backend.constants.UserStatusConstant;
import api.iterator.backend.constants.UserTypeConstant;
import api.iterator.backend.entities.UserMatchRequestEntity;
import api.iterator.backend.models.displays.applicant.UserDisplayModel;
import api.iterator.backend.models.displays.applicant.UserProfileDisplayModel;
import api.iterator.backend.models.displays.request.UpcomingRequestModel;
import api.iterator.backend.repositories.UserMatchRequestRepository;
import api.iterator.backend.repositories.UserProfileRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UpcomingRequestBusinessImpl {
    private static final String APPLICANT = UserTypeConstant.APPLICANT;
    private static final String SHARER = UserTypeConstant.SHARER;
    private static final String APPROVED = MatchStatusConstant.APPROVED;
    private static final String PENDING = MatchStatusConstant.PENDING;
    private static final String INACTIVE = UserStatusConstant.INACTIVE;

    private final UserMatchRequestRepository userMatchRequestRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UpcomingRequestBusinessImpl(UserMatchRequestRepository userMatchRequestRepository, UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userMatchRequestRepository = userMatchRequestRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public List<UpcomingRequestModel> getUpcomingRequests(Long sourceUserId) {
        List<UserMatchRequestEntity> approvedUpcomingRequests = userMatchRequestRepository.findBySourceUserIdAndMatchStatus(sourceUserId, APPROVED);
        if (approvedUpcomingRequests == null) {
            return null;
        }

        List<UpcomingRequestModel> upcomingRequests = new ArrayList<>();
        approvedUpcomingRequests.forEach(upcomingRequest -> {
            Long targetUserId = upcomingRequest.getTargetUserId();

            UserDisplayModel userDisplay = userRepository.getUserDisplay(targetUserId);
            UserProfileDisplayModel userProfileDisplayModel = userProfileRepository.getUserProfileDisplay(targetUserId);

            // Check if user profile is edited and status is active => else return
            if (userProfileDisplayModel == null || Objects.equals(userProfileDisplayModel.getStatus(), INACTIVE)) {
                return;
            }

            UpcomingRequestModel upcomingRequestModel = new UpcomingRequestModel();
            upcomingRequestModel.setTargetUserId(targetUserId);
            upcomingRequestModel.setFirstName(userDisplay.getFirstName());
            upcomingRequestModel.setLastName(userDisplay.getLastName());
            upcomingRequestModel.setJobPostId(upcomingRequest.getJobPostId());
            upcomingRequestModel.setRequestMessage(upcomingRequest.getRequestMessage());

            upcomingRequests.add(upcomingRequestModel);
        });

        return upcomingRequests;
    }

    @Transactional
    public void sendMatchRequest(Long sourceUserId, UpcomingRequestModel upcomingRequestModel) {
        UserDisplayModel userDisplay = userRepository.getUserDisplay(upcomingRequestModel.getTargetUserId());

        String requestMessage = String.format(
                "A match request has been sent to %s %s",
                userDisplay.getFirstName(),
                userDisplay.getLastName()
        );

        UserMatchRequestEntity userMatchRequestEntity = new UserMatchRequestEntity();
        userMatchRequestEntity.setSourceUserId(sourceUserId);
        userMatchRequestEntity.setTargetUserId(upcomingRequestModel.getTargetUserId());
        userMatchRequestEntity.setJobPostId(upcomingRequestModel.getJobPostId());
        userMatchRequestEntity.setMatchStatus(PENDING);
        userMatchRequestEntity.setRequestMessage(requestMessage);

        userMatchRequestRepository.save(userMatchRequestEntity);
    }
}
