package api.iterator.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "job_types")
public class JobTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobType;

    public JobTypeEntity() {

    }

    public Long getId() {
        return id;
    }

    public JobTypeEntity(String jobType) {
        this.jobType = jobType;
    }

    public String getJobType() {
        return jobType;
    }
}
