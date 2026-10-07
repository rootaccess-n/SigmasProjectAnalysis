package rest.api.constellationsreservations.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "refresh_tokens")
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jti_hash",length = 1024)
    private String jtiHash;

    @Column(name = "family_id")
    private String familyID;

    @Column(name = "user_id")
    private Long userID;

    @Column
    private boolean valid;

    @Column(name = "expires_at")
    private Instant expiredAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "old_refresh_time")
    private Instant oldRefreshTime;





}
