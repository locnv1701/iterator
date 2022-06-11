package api.iterator.backend.models.displays.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MatchRequestModel extends RequestModel {
    // user id
    private Long sourceUserId;

    public MatchRequestModel(Long sourceUserId, Long jobPostId, String requestMessage, String matchStatus) {
        this.sourceUserId = sourceUserId;
        this.jobPostId = jobPostId;
        this.requestMessage = requestMessage;
        this.matchStatus = matchStatus;
    }


    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
}
