package api.iterator.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "business_streams")
public class BusinessStreamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessStream;

    public Long getId() {
        return id;
    }

    public BusinessStreamEntity(String businessStream) {
        this.businessStream = businessStream;
    }

    public BusinessStreamEntity() {

    }
}
