package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.MatchRequestBusinessImpl;
import api.iterator.backend.business.validators.UserValidator;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.displays.request.MatchRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MatchRequestController {
    private final MatchRequestBusinessImpl matchRequestBusinessImpl;
    private final UserValidator userValidator;

    public MatchRequestController(MatchRequestBusinessImpl matchRequestBusinessImpl, UserValidator userValidator) {
        this.matchRequestBusinessImpl = matchRequestBusinessImpl;
        this.userValidator = userValidator;
    }

    @GetMapping("/display/match-request/{user_id}")
    ResponseEntity<List<MatchRequestModel>> displayAllMatchRequests(
            @PathVariable("user_id") Long userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) throws SystemException {
        userValidator.checkUserExist(userId);

        List<MatchRequestModel> data = matchRequestBusinessImpl.getAllMatchRequests(userId, page, size);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
