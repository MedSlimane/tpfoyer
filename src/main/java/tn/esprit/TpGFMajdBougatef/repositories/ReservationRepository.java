package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

    @Query("SELECT r FROM Reservation r WHERE FUNCTION('YEAR', r.anneeUniversitaire) = FUNCTION('YEAR', :annee) " +
	    "AND r.chambre.bloc.foyer.universite.nomUniversite = :nom")
    List<Reservation> findByAcademicYearAndUniversite(@Param("annee") Date anneeUniversitaire,
						      @Param("nom") String nomUniversite);

    // Active reservation for a student by CIN
    @Query("SELECT r FROM Reservation r JOIN r.etudiants e WHERE e.cin = :cin AND r.estValide = true")
    Optional<Reservation> findActiveByEtudiantCin(@Param("cin") long cin);
}
