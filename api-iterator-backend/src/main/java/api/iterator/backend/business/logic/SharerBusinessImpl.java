package api.iterator.backend.business.logic;

import api.iterator.backend.constants.JobPostConstant;
import api.iterator.backend.entities.BusinessStreamEntity;
import api.iterator.backend.entities.CompanyEntity;
import api.iterator.backend.entities.JobPostEntity;
import api.iterator.backend.entities.JobTypeEntity;
import api.iterator.backend.exceptions.JobPostNotExistException;
import api.iterator.backend.exceptions.MissingFieldException;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.displays.sharer.CompanyModel;
import api.iterator.backend.models.displays.sharer.JobPostDisplayModel;
import api.iterator.backend.repositories.BusinessStreamRepository;
import api.iterator.backend.repositories.CompanyRepository;
import api.iterator.backend.repositories.JobPostRepository;
import api.iterator.backend.repositories.JobTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SharerBusinessImpl {
    private static final String DELETE = JobPostConstant.DELETE;
    private static final String UPDATE = JobPostConstant.UPDATE;
    private static final String CREATE = JobPostConstant.CREATE;


    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final JobTypeRepository jobTypeRepository;
    private final BusinessStreamRepository businessStreamRepository;

    public SharerBusinessImpl(JobPostRepository jobPostRepository,
                              CompanyRepository companyRepository,
                              JobTypeRepository jobTypeRepository,
                              BusinessStreamRepository businessStreamRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.businessStreamRepository = businessStreamRepository;
    }

    @Transactional
    public Object editJobPost(Long jobPostId, String function, JobPostDisplayModel jobPostDisplayModel, Long sharerId) throws JobPostNotExistException, MissingFieldException {
        Object responseModel = null;
        JobPostEntity jobPostEntity;
        CompanyEntity companyEntity;
        switch (function) {
            case DELETE:
                if (jobPostId == null) {
                    throw new MissingFieldException("job_post_id");
                }
                // check job post exist, if exist delete job post and company, else throw JobPostNotExistException
                if (jobPostRepository.existsById(jobPostId)) {
                    long companyId = jobPostRepository.findCompanyIdByJobPostId(jobPostId);
                    jobPostRepository.deleteById(jobPostId);
                    companyRepository.deleteById(companyId);

                    responseModel = new UserResponseModel(
                            HttpStatus.OK.value(),
                            "DELETE job post successfully!"
                    );
                } else throw new JobPostNotExistException(jobPostId);
                break;

            case UPDATE:
                if (jobPostId == null) {
                    throw new MissingFieldException("job_post_id");
                }
                // find job post and company by job post id
                if (jobPostRepository.existsById(jobPostId)) {
                jobPostEntity = jobPostRepository.findById(jobPostId).orElse(new JobPostEntity(jobPostId));
                companyEntity = companyRepository.findById(jobPostEntity.getCompanyId()).get();
                responseModel = updateOrCreateJobPost(jobPostEntity, companyEntity, jobPostDisplayModel, sharerId);
                } else throw new JobPostNotExistException(jobPostId);
                break;

            case CREATE:
                // create new job post and company
                companyEntity = new CompanyEntity(jobPostDisplayModel.getCompany().getCompanyName());
                jobPostEntity = new JobPostEntity();
                jobPostDisplayModel.setJobPostId(jobPostEntity.getId());
                responseModel = updateOrCreateJobPost(jobPostEntity, companyEntity, jobPostDisplayModel, sharerId);
                break;
        }

        return responseModel;
    }

    private JobPostDisplayModel updateOrCreateJobPost(JobPostEntity jobPostEntity, CompanyEntity companyEntity, JobPostDisplayModel jobPostDisplayModel, Long sharerId) {
        // set not null and blank job post properties
        jobPostEntity.setUserId(sharerId);
        jobPostEntity.setJobSalary(jobPostDisplayModel.getJobSalary());
        jobPostEntity.setJobDescription(jobPostDisplayModel.getJobDescription());
        jobPostEntity.setJobLocation(jobPostDisplayModel.getJobLocation());
        jobPostEntity.setAvailable(jobPostDisplayModel.getIsAvailable());


        CompanyModel companyModel = jobPostDisplayModel.getCompany();
        String businessStream = companyModel.getBusinessStream();

        // input Business stream not found => create new business stream
        long businessStreamId = businessStreamRepository.findIdByBusinessStream(businessStream).orElseGet(() -> {
            BusinessStreamEntity newBusinessStreamEntity = businessStreamRepository.save(new BusinessStreamEntity(businessStream));

            return newBusinessStreamEntity.getId();
        });

        // set not null and blank company properties
        companyEntity.setCompanyName(jobPostDisplayModel.getCompany().getCompanyName());
        companyEntity.setBusinessStreamId(businessStreamId);
        companyEntity.setProfileDescription(companyModel.getProfileDescription());

        companyRepository.save(companyEntity);

        long companyId = companyEntity.getId();
        // set new company id
        jobPostEntity.setCompanyId(companyId);

        // input Job type not found => create new job type
        String jobType = jobPostDisplayModel.getJobType();
        long jobTypeId = jobTypeRepository.findIdByJobType(jobType).orElseGet(() -> {
            JobTypeEntity newJobTypeEntity = jobTypeRepository.save(new JobTypeEntity(jobType));

            return newJobTypeEntity.getId();
        });
        // set new job type id
        jobPostEntity.setJobTypeId(jobTypeId);

        jobPostRepository.save(jobPostEntity);

        // Return new display model after create or edit job post
        return new JobPostDisplayModel(
                jobPostEntity.getId(),
                jobPostEntity.getUserId(),
                jobType,
                jobPostEntity.getJobSalary(),
                jobPostEntity.getJobDescription(),
                jobPostEntity.getJobLocation(),
                jobPostEntity.getAvailable(),
                new CompanyModel(
                        companyEntity.getCompanyName(),
                        businessStream,
                        companyEntity.getProfileDescription()
                )
        );
    }
}
