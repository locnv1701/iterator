package api.iterator.backend.exceptions;

/**
 * Custom exception for username duplication
 */
public class DuplicateUsernameException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Duplicate username";
    private static final String DETAIL_MESSAGE = "Username %s has existed, please try another username.";

    public DuplicateUsernameException(String username) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, username));
    }
}
