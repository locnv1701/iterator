package api.iterator.backend.exceptions;

public class MissingFieldException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Missing field exception";
    private static final String DETAIL_MESSAGE = "Field %s is null.";

    public MissingFieldException(String field) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, field));
    }
}


