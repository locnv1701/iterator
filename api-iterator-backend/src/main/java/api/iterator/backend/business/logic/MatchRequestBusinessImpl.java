package api.iterator.backend.business.logic;

import api.iterator.backend.constants.MatchStatusConstant;
import api.iterator.backend.entities.UserMatchRequestEntity;
import api.iterator.backend.models.displays.applicant.UserDisplayModel;
import api.iterator.backend.models.displays.request.MatchRequestModel;
import api.iterator.backend.repositories.JobPostRepository;
import api.iterator.backend.repositories.UserMatchRequestRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MatchRequestBusinessImpl {
    private static final String APPROVED = MatchStatusConstant.APPROVED;

    private final UserMatchRequestRepository userMatchRequestRepository;
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;

    public MatchRequestBusinessImpl(UserMatchRequestRepository userMatchRequestRepository, UserRepository userRepository, JobPostRepository jobPostRepository) {
        this.userMatchRequestRepository = userMatchRequestRepository;
        this.userRepository = userRepository;
        this.jobPostRepository = jobPostRepository;
    }

    public List<MatchRequestModel> getAllMatchRequests(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserMatchRequestEntity> userRequestPage = userMatchRequestRepository
                .findBySourceUserIdOrTargetUserId(userId, userId, pageRequest);
        if (userRequestPage == null) {
            return null;
        }

        // Return match request with source user id or target user id is user id
        return userRequestPage.getContent().stream()
                .filter(request -> Objects.equals(request.getMatchStatus(), APPROVED))
                .map(matchRq -> {
                    UserDisplayModel userDisplay;
                    Long matchedUserId;
                    if (Objects.equals(userId, matchRq.getSourceUserId())) {
                        matchedUserId = matchRq.getTargetUserId();
                        userDisplay = userRepository.getUserDisplay(matchRq.getTargetUserId());
                    } else {
                        matchedUserId = matchRq.getSourceUserId();
                        userDisplay = userRepository.getUserDisplay(matchRq.getSourceUserId());
                    }

                    MatchRequestModel matchRequestModel = new MatchRequestModel(
                            matchedUserId,
                            matchRq.getJobPostId(),
                            matchRq.getRequestMessage(),
                            matchRq.getMatchStatus()
                    );

                    matchRequestModel.setFirstName(userDisplay.getFirstName());
                    matchRequestModel.setLastName(userDisplay.getLastName());
                    jobPostRepository.findById(matchRq.getJobPostId())
                            .ifPresent(jobPost -> matchRequestModel.setJobName(jobPost.getJobType()));

                    return matchRequestModel;
                }).collect(Collectors.toList());
    }
}
