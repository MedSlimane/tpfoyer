package tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces;

import tn.esprit.TpGFMajdBougatef.entities.Universite;

import java.util.List;

public interface UniversiteServiceInterfaces {
    List<Universite> retrieveAllUniversities();
    Universite addUniversite(Universite u);
    Universite updateUniversite(Universite u);
    Universite retrieveUniversite(long idUniversite);
    // Services avancés
    Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite);
    Universite desaffecterFoyerAUniversite(long idUniversite);
}
