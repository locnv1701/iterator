package api.iterator.backend.models.displays;

import api.iterator.backend.models.UserProfileModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfilePageModel extends UserProfileModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String entryLevel;
    private Integer experienceYear;

    public UserProfilePageModel() {
        super();
    }

    public UserProfilePageModel(String profileDisplayName, Integer age, String gender, String location, String status, String githubLink, String facebookLink, String instagramLink, String twitterLink) {
        super(profileDisplayName, age, gender, location, status, githubLink, facebookLink, instagramLink, twitterLink);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
