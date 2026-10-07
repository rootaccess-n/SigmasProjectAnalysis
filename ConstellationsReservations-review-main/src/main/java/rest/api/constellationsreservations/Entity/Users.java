package rest.api.constellationsreservations.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rest.api.constellationsreservations.Enums.Role;
import rest.api.constellationsreservations.Enums.Subscription;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(name = "subscription_type")
    @Enumerated(EnumType.STRING)
    private Subscription subscriptionOnType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userReserve")
    private List<Reservations> reservations = new ArrayList<>();

    //for Converter
    public Users(String username) {
        this.username = username;
    }
}

