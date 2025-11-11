package tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces;

import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.List;

public interface ReservationServiceInterfaces {
    List<Reservation> retrieveAllReservation();
    Reservation updateReservation(Reservation res);
    Reservation retrieveReservation(String idReservation);

    // Partie 5
    java.util.List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(java.util.Date anneeUniversite, String nomUniversite);
    // Ajouter une réservation (affecter chambre + étudiant)
    Reservation ajouterReservation(long idChambre, long cinEtudiant);
    // Annuler une réservation par CIN étudiant
    Reservation annulerReservation(long cinEtudiant);
}
