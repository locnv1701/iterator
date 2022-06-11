package api.iterator.backend.entities;

import api.iterator.backend.models.displays.sharer.CompanyModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Column(name = "business_stream_id")
    private Long businessStreamId;

    @OneToOne
    @JoinColumn(name = "business_stream_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BusinessStreamEntity business;

    private String profileDescription;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public CompanyEntity() {

    }

    public CompanyEntity(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setBusinessStreamId(Long businessStreamId) {
        this.businessStreamId = businessStreamId;
    }

    public void setBusiness(BusinessStreamEntity business) {
        this.business = business;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
