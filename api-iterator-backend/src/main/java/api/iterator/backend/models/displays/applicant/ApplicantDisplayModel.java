package api.iterator.backend.models.displays.applicant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApplicantDisplayModel {
    private Long userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String location;
    private String entryLevel;
    private Integer experienceYear;
    private ApplicantEducationModel education;
    private List<ApplicantExperienceModel> experiences;
    private List<SkillModel> skills;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEntryLevel() {
        return entryLevel;
    }

    public void setEntryLevel(String entryLevel) {
        this.entryLevel = entryLevel;
    }

    public Integer getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
    }

    public ApplicantEducationModel getEducation() {
        return education;
    }

    public void setEducation(ApplicantEducationModel education) {
        this.education = education;
    }

    public List<ApplicantExperienceModel> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ApplicantExperienceModel> experiences) {
        this.experiences = experiences;
    }

    public List<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillModel> skills) {
        this.skills = skills;
    }
}
