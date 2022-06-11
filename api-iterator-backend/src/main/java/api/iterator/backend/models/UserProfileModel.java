package api.iterator.backend.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfileModel {
    protected String profileDisplayName;

    protected Integer age;

    protected String gender;

    protected String location;

    protected String status;

    protected String githubLink;

    protected String facebookLink;

    protected String instagramLink;

    protected String twitterLink;

    protected UserProfileModel() {

    }

    protected UserProfileModel(String profileDisplayName, Integer age, String gender, String location, String status,
                               String githubLink, String facebookLink, String instagramLink, String twitterLink) {
        this.profileDisplayName = profileDisplayName;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.status = status;
        this.githubLink = githubLink;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
    }

    public String getProfileDisplayName() {
        return profileDisplayName;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }
}
