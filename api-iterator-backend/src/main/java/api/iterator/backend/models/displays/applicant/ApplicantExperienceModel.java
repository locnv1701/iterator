package api.iterator.backend.models.displays.applicant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApplicantExperienceModel {
    private String jobTitle;
    private String companyName;
    private String startDate;
    private String endDate;
    private String projectSelfDescription;

    public ApplicantExperienceModel() {

    }

    public ApplicantExperienceModel(String jobTitle, String companyName, String startDate, String endDate, String projectSelfDescription) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectSelfDescription = projectSelfDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getProjectSelfDescription() {
        return projectSelfDescription;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setProjectSelfDescription(String projectSelfDescription) {
        this.projectSelfDescription = projectSelfDescription;
    }
}
