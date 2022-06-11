package api.iterator.backend.models.displays.sharer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobPostDisplayModel {
    private Long jobPostId;
    private Long sharerId;
    private String jobType;
    private Integer jobSalary;
    private String jobDescription;
    private String jobLocation;

    @JsonProperty("is_available")
    private Boolean isAvailable;

    CompanyModel company;

    public JobPostDisplayModel() {

    }
    public JobPostDisplayModel(Long jobPostId, Long sharerId, String jobType,
                               Integer jobSalary, String jobDescription, String jobLocation,
                               Boolean isAvailable, CompanyModel company) {
        this.jobPostId = jobPostId;
        this.sharerId = sharerId;
        this.jobType = jobType;
        this.jobSalary = jobSalary;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.isAvailable = isAvailable;
        this.company = company;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }

    public Long getSharerId() {
        return sharerId;
    }

    public void setSharerId(Long sharerId) {
        this.sharerId = sharerId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Integer jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
