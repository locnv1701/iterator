package api.iterator.backend.entities;

import api.iterator.backend.repositories.CompanyRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_posts")
public class JobPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(name = "job_type_id")
    private Long jobTypeId;

    @OneToOne
    @JoinColumn(name = "job_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    JobTypeEntity job;

    private Long companyId;
    private Integer jobSalary;
    private String jobDescription;
    private String jobLocation;
    private Boolean isAvailable;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public JobPostEntity(){

    }

    public JobPostEntity(Long jobPostId) {
        this.id = jobPostId;
    }

    public void JobPostEntity(Long jobPostId) {
        this.id = jobPostId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setAvailable(Boolean available) {
        this.isAvailable = available;
    }


    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJob(JobTypeEntity job) {
        this.job = job;
    }

    public String getJobType() {
        return job.getJobType();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Integer getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Integer jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }
}
