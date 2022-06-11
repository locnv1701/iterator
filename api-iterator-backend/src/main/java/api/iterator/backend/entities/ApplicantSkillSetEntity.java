package api.iterator.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "applicant_skill_sets")
public class ApplicantSkillSetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "skill_id")
    private Long skillId;

    @OneToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SkillSetEntity skill;

    // Standard level range: 1 -> 10
    @Column(name = "level")
    @Min(1)
    @Max(10)
    private Integer level;

    public ApplicantSkillSetEntity(long applicantId) {
        this.applicantId = applicantId;
    }

    public ApplicantSkillSetEntity() {

    }

    public Long getId() {
        return id;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public SkillSetEntity getSkill() {
        return skill;
    }

    public void setSkill(SkillSetEntity skill) {
        this.skill = skill;
    }


}
