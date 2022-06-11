package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.UpcomingRequestBusinessImpl;
import api.iterator.backend.business.validators.RequestValidator;
import api.iterator.backend.business.validators.SharerValidator;
import api.iterator.backend.business.validators.UserValidator;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.displays.request.UpcomingRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UpcomingRequestController {
    private final UpcomingRequestBusinessImpl upcomingRequestBusinessImpl;
    private final UserValidator userValidator;
    private final SharerValidator sharerValidator;
    private final RequestValidator requestValidator;

    public UpcomingRequestController(UpcomingRequestBusinessImpl upcomingRequestBusinessImpl, UserValidator userValidator, SharerValidator sharerValidator, RequestValidator requestValidator) {
        this.upcomingRequestBusinessImpl = upcomingRequestBusinessImpl;
        this.userValidator = userValidator;
        this.sharerValidator = sharerValidator;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/upcoming-request/{user_id}")
    public ResponseEntity<UserResponseModel> sendMatchRequest(
            @PathVariable("user_id") Long userId,
            @RequestBody UpcomingRequestModel upcomingRequestModel
    ) throws SystemException {
        // Check existence of source user id, target user id and job post
        userValidator.checkUserExist(userId);
        userValidator.checkUserExist(upcomingRequestModel.getTargetUserId());
        sharerValidator.checkJobPostExist(upcomingRequestModel.getJobPostId());
        // Check existence of match request
        requestValidator.checkRequestExist(userId, upcomingRequestModel.getTargetUserId(), upcomingRequestModel.getJobPostId());

        upcomingRequestBusinessImpl.sendMatchRequest(userId, upcomingRequestModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Send match request successfully!"),
                HttpStatus.OK
        );
    }

    @GetMapping("/display/upcoming-request/{user_id}")
    public ResponseEntity<List<UpcomingRequestModel>> displayUpcomingRequest(@PathVariable("user_id") Long userId) throws SystemException {
        userValidator.checkUserExist(userId);
        List<UpcomingRequestModel> upcomingRequests = upcomingRequestBusinessImpl.getUpcomingRequests(userId);

        return new ResponseEntity<>(upcomingRequests, HttpStatus.OK);
    }
}
