package api.iterator.backend.exceptions;

public class MatchRequestNotExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Match request not exist";
    private static final String DETAIL_MESSAGE = "Match request not exist, please check your input path and params.";

    public MatchRequestNotExistException() {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(DETAIL_MESSAGE);
    }
}
