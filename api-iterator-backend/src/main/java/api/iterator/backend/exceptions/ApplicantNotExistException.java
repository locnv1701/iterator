package api.iterator.backend.exceptions;

public class ApplicantNotExistException extends SystemException{
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Applicant not exist";
    private static final String DETAIL_MESSAGE = "Id applicant %d not exist.";

    public ApplicantNotExistException(long id) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, id));
    }
}
