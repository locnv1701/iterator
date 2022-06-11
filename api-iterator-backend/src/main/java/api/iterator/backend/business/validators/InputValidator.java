package api.iterator.backend.business.validators;

import api.iterator.backend.constants.RegexConstant;
import api.iterator.backend.exceptions.InputNotValidException;
import org.springframework.stereotype.Service;

@Service
public class InputValidator {
    private static final String REGEX_EMAIL = RegexConstant.EMAIL;
    private static final String REGEX_PHONE_NUMBER = RegexConstant.PHONE_NUMBER;
    private static final String REGEX_SOCIAL_LINK = RegexConstant.SOCIAL_LINK;

    public void checkValidEmailAddress(String email) throws InputNotValidException {
        if (!email.matches(REGEX_EMAIL))
            throw new InputNotValidException("email " + email);
    }

    public void checkValidPhoneNumber(String phoneNumber) throws InputNotValidException {
        if (!phoneNumber.matches(REGEX_PHONE_NUMBER))
            throw new InputNotValidException("phone number " + phoneNumber);
    }

    public void checkValidSocialLink(String url) throws InputNotValidException {
        if (!url.matches(REGEX_SOCIAL_LINK))
            throw new InputNotValidException("path url " + url);
    }
}
