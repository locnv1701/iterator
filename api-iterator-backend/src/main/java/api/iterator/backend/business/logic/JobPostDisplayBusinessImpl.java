package api.iterator.backend.business.logic;

import api.iterator.backend.constants.UserStatusConstant;
import api.iterator.backend.entities.JobPostEntity;
import api.iterator.backend.entities.JobTypeEntity;
import api.iterator.backend.models.displays.applicant.UserProfileDisplayModel;
import api.iterator.backend.models.displays.sharer.CompanyModel;
import api.iterator.backend.models.displays.sharer.JobPostDisplayModel;
import api.iterator.backend.models.displays.sharer.JobPostFilterModel;
import api.iterator.backend.repositories.CompanyRepository;
import api.iterator.backend.repositories.JobPostRepository;
import api.iterator.backend.repositories.UserMatchRequestRepository;
import api.iterator.backend.repositories.UserProfileRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostDisplayBusinessImpl {
    private static final String ACTIVE = UserStatusConstant.ACTIVE;
    private static final String INACTIVE = UserStatusConstant.INACTIVE;

    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMatchRequestRepository userMatchRequestRepository;

    public JobPostDisplayBusinessImpl(JobPostRepository jobPostRepository, CompanyRepository companyRepository, UserProfileRepository userProfileRepository, UserMatchRequestRepository userMatchRequestRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
        this.userProfileRepository = userProfileRepository;
        this.userMatchRequestRepository = userMatchRequestRepository;
    }

    public JobPostDisplayModel getJobPostByJobPostId(Long jobPostId) {
        JobPostEntity jobPostEntity = jobPostRepository.findById(jobPostId).get();
        return getJobPostDisplayModel(jobPostEntity);
    }

    public List<JobPostDisplayModel> getJobPostsByFilter(Long userId, JobPostFilterModel jobPostFilterModel, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<JobPostEntity> jobPostPage;
        if (jobPostFilterModel == null) {
            jobPostPage = jobPostRepository.findAll(pageRequest);
        } else {
            jobPostPage = jobPostRepository.findAll(getJpaExample(jobPostFilterModel), pageRequest);
        }

        if (jobPostPage == null) {
            return null;
        }

        List<JobPostEntity> jobPosts = jobPostPage.getContent();

        List<JobPostDisplayModel> displayModels = new ArrayList<>();
        jobPosts.forEach(jobPost -> {
            UserProfileDisplayModel userProfileDisplayModel = userProfileRepository.getUserProfileDisplay(jobPost.getUserId());

            // Check if user profile is edited and status is active and job post is available => else return
            if (userProfileDisplayModel == null || Objects.equals(userProfileDisplayModel.getStatus(), INACTIVE)
                    || !jobPost.getAvailable()) {
                return;
            }

            // Check if user match request not exist => else return
            if (userMatchRequestRepository.findBySourceUserIdAndTargetUserIdAndJobPostId(userId, jobPost.getUserId(), jobPost.getId()) != null)
                return;

            displayModels.add(getJobPostDisplayModel(jobPost));
        });

        return displayModels;
    }

    public List<JobPostDisplayModel> getJobPostsBySharerId(Long userId) {
        List<JobPostEntity> jobPosts = jobPostRepository.findByUserId(userId);
        if (jobPosts == null) {
            return null;
        }

        List<JobPostDisplayModel> displayModels = new ArrayList<>();
        jobPosts.forEach(jobPost -> {
            displayModels.add(getJobPostDisplayModel(jobPost));
        });

        return displayModels;
    }

    private JobPostDisplayModel getJobPostDisplayModel(JobPostEntity jobPost) {
        JobPostDisplayModel jobPostDisplayModel = new JobPostDisplayModel();

        CompanyModel company = companyRepository.getCompanyDisplay(jobPost.getCompanyId()).orElse(new CompanyModel());

        jobPostDisplayModel.setJobPostId(jobPost.getId());
        jobPostDisplayModel.setSharerId(jobPost.getUserId());
        jobPostDisplayModel.setJobType(jobPost.getJobType());
        jobPostDisplayModel.setCompany(company);
        jobPostDisplayModel.setJobSalary(jobPost.getJobSalary());
        jobPostDisplayModel.setJobDescription(jobPost.getJobDescription());
        jobPostDisplayModel.setJobLocation(jobPost.getJobLocation());
        jobPostDisplayModel.setAvailable(jobPost.getAvailable());

        return jobPostDisplayModel;
    }

    private Example<JobPostEntity> getJpaExample(JobPostFilterModel jobPostFilterModel) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        JobPostEntity jobPost = new JobPostEntity();
        jobPost.setJob(new JobTypeEntity(jobPostFilterModel.getJobType()));
        jobPost.setJobSalary(jobPostFilterModel.getJobSalary());
        jobPost.setJobLocation(jobPostFilterModel.getJobLocation());

        return Example.of(jobPost, matcher);
    }
}
