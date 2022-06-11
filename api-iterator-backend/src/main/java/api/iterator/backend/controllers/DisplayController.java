package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.ApplicantDisplayBusinessImpl;
import api.iterator.backend.business.logic.JobPostDisplayBusinessImpl;
import api.iterator.backend.business.validators.JobPostValidator;
import api.iterator.backend.business.validators.UserValidator;
import api.iterator.backend.business.logic.UserBusinessImpl;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.displays.applicant.ApplicantDisplayModel;
import api.iterator.backend.models.displays.applicant.ApplicantFilterModel;
import api.iterator.backend.models.displays.UserProfilePageModel;
import api.iterator.backend.models.displays.sharer.JobPostDisplayModel;
import api.iterator.backend.models.displays.sharer.JobPostFilterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/display")
public class DisplayController {
    private final ApplicantDisplayBusinessImpl applicantDisplayBusinessImpl;
    private final JobPostDisplayBusinessImpl jobPostDisplayBusinessImpl;
    private final UserValidator userValidator;
    private final UserBusinessImpl userBusinessImpl;
    private final JobPostValidator jobPostValidator;

    public DisplayController(ApplicantDisplayBusinessImpl applicantDisplayBusinessImpl, JobPostDisplayBusinessImpl jobPostDisplayBusinessImpl, UserValidator userValidator, UserBusinessImpl userBusinessImpl, JobPostValidator jobPostValidator) {
        this.applicantDisplayBusinessImpl = applicantDisplayBusinessImpl;
        this.jobPostDisplayBusinessImpl = jobPostDisplayBusinessImpl;
        this.userValidator = userValidator;
        this.userBusinessImpl = userBusinessImpl;
        this.jobPostValidator = jobPostValidator;
    }

    @GetMapping("/user-profile/{user_id}")
    public ResponseEntity<UserProfilePageModel> displayUserProfilePage(@PathVariable("user_id") Long userId) throws SystemException {
        UserProfilePageModel profilePageModel = userBusinessImpl.displayProfilePage(userId);

        return new ResponseEntity<>(profilePageModel, HttpStatus.OK);
    }

    @PostMapping("/applicant/{user_id}")
    public ResponseEntity<List<ApplicantDisplayModel>> displayApplicants(
            @PathVariable("user_id") Long userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("job_post_id") Long jobPostId,
            @RequestBody(required = false) ApplicantFilterModel applicantFilterModel
    ) {
        List<ApplicantDisplayModel> displayModels = applicantDisplayBusinessImpl.getApplicantsByFilter(applicantFilterModel, page, size, userId, jobPostId);

        return new ResponseEntity<>(displayModels, HttpStatus.OK);
    }

    @GetMapping("/applicant-profile/{user_id}")
    public ResponseEntity<ApplicantDisplayModel> displayApplicantProfile(@PathVariable("user_id") Long userId) throws SystemException {
        userValidator.checkUserExist(userId);
        ApplicantDisplayModel displayModel = applicantDisplayBusinessImpl.getApplicantDisplayModelByUserId(userId);

        return new ResponseEntity<>(displayModel, HttpStatus.OK);
    }

    @PostMapping("/sharer/job-post/{user_id}")
    public ResponseEntity<List<JobPostDisplayModel>> displayJobPosts(
            @PathVariable("user_id") Long userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestBody(required = false)JobPostFilterModel jobPostFilterModel
    ) {
        List<JobPostDisplayModel> displayModels = jobPostDisplayBusinessImpl.getJobPostsByFilter(userId, jobPostFilterModel, page, size);

        return new ResponseEntity<>(displayModels, HttpStatus.OK);
    }

    @GetMapping("/sharer-profile/{user_id}/job-post")
    public ResponseEntity<List<JobPostDisplayModel>> displaySharerProfileJobPosts(@PathVariable("user_id") Long userId) throws SystemException {
        userValidator.checkUserExist(userId);
        List<JobPostDisplayModel> displayModels = jobPostDisplayBusinessImpl.getJobPostsBySharerId(userId);

        return new ResponseEntity<>(displayModels, HttpStatus.OK);
    }

    @GetMapping("/job-post/{job_post_id}")
    public ResponseEntity<JobPostDisplayModel> displayJobPost(@PathVariable("job_post_id") Long jobPostId) throws SystemException {
        jobPostValidator.checkJobPostNotExist(jobPostId);
        JobPostDisplayModel displayModels = jobPostDisplayBusinessImpl.getJobPostByJobPostId(jobPostId);

        return new ResponseEntity<>(displayModels, HttpStatus.OK);
    }

}
