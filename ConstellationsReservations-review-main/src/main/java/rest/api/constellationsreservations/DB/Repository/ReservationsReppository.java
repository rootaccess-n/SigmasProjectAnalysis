package rest.api.constellationsreservations.DB.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.api.constellationsreservations.Entity.Reservations;

public interface ReservationsReppository extends JpaRepository<Reservations,Integer> {
}
