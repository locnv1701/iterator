package api.iterator.backend.models.displays.applicant;

public class UserProfileDisplayModel {
    private Integer age;
    private String gender;
    private String location;
    private String status;

    public UserProfileDisplayModel(Integer age, String gender, String location, String status) {
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
