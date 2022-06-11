package api.iterator.backend.models.displays.sharer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CompanyModel {
    private String companyName;
    private String businessStream;
    private String profileDescription;

    public CompanyModel() {

    }

    public CompanyModel(String companyName, String businessStream, String profileDescription) {
        this.companyName = companyName;
        this.businessStream = businessStream;
        this.profileDescription = profileDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBusinessStream() {
        return businessStream;
    }

    public String getProfileDescription() {
        return profileDescription;
    }
}
