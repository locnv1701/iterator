package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.IncomingRequestBusinessImpl;
import api.iterator.backend.business.validators.RequestValidator;
import api.iterator.backend.business.validators.UserValidator;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.displays.request.IncomingRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomingRequestController {
    private final UserValidator userValidator;
    private final IncomingRequestBusinessImpl incomingRequestBusiness;

    public IncomingRequestController(UserValidator userValidator, IncomingRequestBusinessImpl incomingRequestBusiness, RequestValidator requestValidator) {
        this.userValidator = userValidator;
        this.incomingRequestBusiness = incomingRequestBusiness;
    }


    @GetMapping("/display/incoming-request/{user_id}")
    public ResponseEntity<List<IncomingRequestModel>> displayIncomingRequest(@PathVariable("user_id") Long userId) throws SystemException {
        userValidator.checkUserExist(userId);
        List<IncomingRequestModel> incomingRequests = incomingRequestBusiness.getIncomingRequests(userId);

        return new ResponseEntity<>(incomingRequests, HttpStatus.OK);
    }

    @PutMapping("/incoming-request/{user_id}")
    public ResponseEntity<UserResponseModel> interactRequest(
            @RequestBody IncomingRequestModel incomingRequestModel,
            @PathVariable("user_id") long userTargetId,
            @RequestParam("source_user_id") long sourceUserId,
            @RequestParam("job_post_id") long jobPostId
    ) throws SystemException {
        incomingRequestBusiness.setStatusForRequest(userTargetId, sourceUserId, jobPostId, incomingRequestModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Set status for match request successfully!"),
                HttpStatus.OK
        );
    }
}
