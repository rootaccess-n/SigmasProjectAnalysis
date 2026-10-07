package rest.api.constellationsreservations.DB.Repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import rest.api.constellationsreservations.Entity.Token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE refresh_tokens
    SET valid = false ,
     expires_at = :now
    WHERE jti_hash = :jti
    """, nativeQuery = true)
    void revokeToken(@Param("jti") String jti, @Param("now") Instant now);

    @Transactional
    @Modifying
    @Query(value = """
    UPDATE refresh_tokens 
    SET valid = false ,
     expires_at = :now
    WHERE family_id = :family_id
    """,nativeQuery = true)
    int revokeFamilyTokens( @Param("family_id") String family_id, @Param("now") Instant now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Token> findByJtiHash(String jti);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Token> findAllByUserID(Long userId);







}
