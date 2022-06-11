package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.UserBusinessImpl;
import api.iterator.backend.business.logic.UserRegisterBusinessImpl;
import api.iterator.backend.business.validators.InputValidator;
import api.iterator.backend.business.validators.UserValidator;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.UserProfileModel;
import api.iterator.backend.models.UserRegisterModel;
import api.iterator.backend.models.UserResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRegisterBusinessImpl userRegisterBusinessImpl;
    private final UserValidator userValidator;
    private final UserBusinessImpl userBusinessImpl;
    private final InputValidator inputValidator;

    UserController(UserRegisterBusinessImpl userRegisterBusinessImpl, UserValidator userValidator, UserBusinessImpl userBusinessImpl, InputValidator inputValidator) {
        this.userRegisterBusinessImpl = userRegisterBusinessImpl;
        this.userValidator = userValidator;
        this.userBusinessImpl = userBusinessImpl;
        this.inputValidator = inputValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseModel> register(@RequestBody @Valid UserRegisterModel userRegisterModel) throws SystemException {
        userValidator.checkDuplicateUsername(userRegisterModel.getUserName());
        inputValidator.checkValidPhoneNumber(userRegisterModel.getPhoneNumber());
        inputValidator.checkValidEmailAddress(userRegisterModel.getEmail());
        userValidator.checkDuplicateEmail(userRegisterModel.getEmail());

        userRegisterBusinessImpl.register(userRegisterModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Register successfully!"),
                HttpStatus.OK
        );
    }

    @PutMapping("/edit-profile/{user_id}")
    public ResponseEntity<UserResponseModel> editProfile(@RequestBody @Valid UserProfileModel userProfileModel, @PathVariable("user_id") long userId) throws SystemException {
        userValidator.checkUserExist(userId);
        if (StringUtils.isNotBlank(userProfileModel.getFacebookLink())) {
            inputValidator.checkValidSocialLink(userProfileModel.getFacebookLink());
        }
        if (StringUtils.isNotBlank(userProfileModel.getGithubLink())) {
            inputValidator.checkValidSocialLink(userProfileModel.getGithubLink());
        }
        if (StringUtils.isNotBlank(userProfileModel.getTwitterLink())) {
            inputValidator.checkValidSocialLink(userProfileModel.getTwitterLink());
        }
        if (StringUtils.isNotBlank(userProfileModel.getInstagramLink())) {
            inputValidator.checkValidSocialLink(userProfileModel.getInstagramLink());
        }

        userBusinessImpl.editProfile(userProfileModel, userId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Edit profile successfully!"),
                HttpStatus.OK
        );
    }
}
