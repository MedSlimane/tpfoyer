package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.Etudiant;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.repositories.EtudiantRepository;
import tn.esprit.TpGFMajdBougatef.repositories.ReservationRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ReservationServiceInterfaces;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImp implements ReservationServiceInterfaces {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Reservation> retrieveAllReservation() { return reservationRepository.findAll(); }

    @Override
    public Reservation updateReservation(Reservation res) { return reservationRepository.save(res); }

    @Override
    public Reservation retrieveReservation(String idReservation) { return reservationRepository.findById(idReservation).orElse(null); }

    // Partie 5
    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite) {
        return reservationRepository.findByAcademicYearAndUniversite(anneeUniversite, nomUniversite);
    }

    @Override
    @Transactional
    public Reservation ajouterReservation(long idChambre, long cinEtudiant) {
        Chambre chambre = chambreRepository.findById(idChambre).orElse(null);
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant).orElse(null);
        if (chambre == null || etudiant == null) {
            return null;
        }
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int year = cal.get(Calendar.YEAR);
        String numReservation = String.format("%d-%s-%d",
                chambre.getNumeroChambre(),
                chambre.getBloc() != null ? chambre.getBloc().getNomBloc() : "",
                year);
        Reservation reservation = Reservation.builder()
                .idReservation(numReservation)
                .anneeUniversitaire(today)
                .estValide(true)
                .chambre(chambre)
                .etudiants(new ArrayList<>())
                .build();
        reservation.getEtudiants().add(etudiant);
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation annulerReservation(long cinEtudiant) {
        return reservationRepository.findActiveByEtudiantCin(cinEtudiant)
                .map(r -> {
                    r.setEstValide(false);
                    // Désaffecter associations
                    r.setChambre(null);
                    if (r.getEtudiants() != null) {
                        r.getEtudiants().clear();
                    }
                    return reservationRepository.save(r);
                })
                .orElse(null);
    }
}
