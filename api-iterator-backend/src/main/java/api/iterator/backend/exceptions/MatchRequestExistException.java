package api.iterator.backend.exceptions;

public class MatchRequestExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Match request existed";
    private static final String DETAIL_MESSAGE = "You had already sent this match request before.";

    public MatchRequestExistException() {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(DETAIL_MESSAGE);
    }
}
