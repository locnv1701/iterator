package api.iterator.backend.models.displays.applicant;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApplicantFilterModel {
    private String entryLevel;
    private Integer experienceYear;
    private List<SkillModel> skillSet;

    public Integer getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
    }

    public String getEntryLevel() {
        return entryLevel;
    }

    public void setEntryLevel(String entryLevel) {
        this.entryLevel = entryLevel;
    }

    public List<SkillModel> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<SkillModel> skillSet) {
        this.skillSet = skillSet;
    }
}
