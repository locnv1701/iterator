package api.iterator.backend.exceptions;

/**
 * Custom exception for user email duplication
 */
public class DuplicateEmailException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Duplicate email";
    private static final String DETAIL_MESSAGE = "Email %s has been registered, please try another email.";

    public DuplicateEmailException(String email) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, email));
    }
}
