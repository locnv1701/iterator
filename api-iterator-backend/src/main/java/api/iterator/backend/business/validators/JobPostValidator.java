package api.iterator.backend.business.validators;

import api.iterator.backend.exceptions.JobPostNotExistException;
import api.iterator.backend.repositories.JobPostRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPostValidator {
    private final JobPostRepository jobPostRepository;

    public JobPostValidator(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public void checkJobPostNotExist(Long id) throws JobPostNotExistException {
        jobPostRepository.findById(id).orElseThrow(() -> new JobPostNotExistException(id));
    }

}
