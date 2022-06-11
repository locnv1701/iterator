package api.iterator.backend.exceptions;

public class JobPostNotExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Job post not exist";
    private static final String DETAIL_MESSAGE = "Job post %d not exist, please try another job post id.";

    public JobPostNotExistException(long jobPostId) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, jobPostId));
    }
}
