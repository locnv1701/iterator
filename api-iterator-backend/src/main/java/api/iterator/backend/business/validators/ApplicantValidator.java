package api.iterator.backend.business.validators;

import api.iterator.backend.exceptions.ApplicantNotExistException;
import api.iterator.backend.repositories.ApplicantRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicantValidator {
    private final ApplicantRepository applicantRepository;

    public ApplicantValidator(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public void checkApplicantExist(Long id) throws ApplicantNotExistException {
        applicantRepository.findById(id).orElseThrow(() -> new ApplicantNotExistException(id));
    }
}
