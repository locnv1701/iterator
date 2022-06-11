package api.iterator.backend.models.displays.sharer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobPostFilterModel {
    private String jobType;
    private Integer jobSalary;
    private String jobLocation;

    public String getJobType() {
        return jobType;
    }

    public Integer getJobSalary() {
        return jobSalary;
    }

    public String getJobLocation() {
        return jobLocation;
    }
}
