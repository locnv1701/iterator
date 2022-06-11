package api.iterator.backend.models.displays.applicant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApplicantEducationModel {
    private String major;
    private String collegeName;
    private String startingDate;
    private String completionDate;
    private Float cgpa;
    private String degreeName;

    public ApplicantEducationModel() {

    }

    public ApplicantEducationModel(String major, String collegeName, String startingDate, String completionDate, Float cgpa, String degreeName) {
        this.major = major;
        this.collegeName = collegeName;
        this.startingDate = startingDate;
        this.completionDate = completionDate;
        this.cgpa = cgpa;
        this.degreeName = degreeName;
    }

    public String getMajor() {
        return major;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public Float getCgpa() {
        return cgpa;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
}
