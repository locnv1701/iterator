package api.iterator.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_types")
public class UserTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userType;

    public String getUserType() {
        return userType;
    }
}
