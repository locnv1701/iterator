package api.iterator.backend.business.validators;

import api.iterator.backend.exceptions.JobPostNotExistException;
import api.iterator.backend.repositories.JobPostRepository;
import org.springframework.stereotype.Service;

@Service
public class SharerValidator {
    private final JobPostRepository jobPostRepository;

    public SharerValidator(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public void checkJobPostExist(Long jobPostId) throws JobPostNotExistException {
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new JobPostNotExistException(jobPostId);
        }
    }
}
