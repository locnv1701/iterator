package api.iterator.backend.exceptions;

public class UserNotExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "User not exist!";
    private static final String DETAIL_MESSAGE = "Id user %d not exist!";

    public UserNotExistException(Long id) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, id));
    }
}


