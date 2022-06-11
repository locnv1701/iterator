package api.iterator.backend.exceptions;

public class InputNotValidException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Invalid input";
    private static final String DETAIL_MESSAGE = "Input %s invalid, please try again";

    public InputNotValidException(String message) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, message));
    }
}