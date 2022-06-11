package api.iterator.backend.repositories;

import api.iterator.backend.entities.CompanyEntity;
import api.iterator.backend.models.displays.sharer.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    @Query("SELECT new api.iterator.backend.models.displays.sharer.CompanyModel(companyName, business.businessStream, profileDescription) " +
            "FROM CompanyEntity WHERE id = :companyId")
    Optional<CompanyModel> getCompanyDisplay(Long companyId);
}
