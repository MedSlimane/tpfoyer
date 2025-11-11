package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Foyer;
import tn.esprit.TpGFMajdBougatef.entities.Universite;
import tn.esprit.TpGFMajdBougatef.repositories.FoyerRepository;
import tn.esprit.TpGFMajdBougatef.repositories.UniversiteRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.UniversiteServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversiteServiceImp implements UniversiteServiceInterfaces {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() { return universiteRepository.findAll(); }

    @Override
    public Universite addUniversite(Universite u) { return universiteRepository.save(u); }

    @Override
    public Universite updateUniversite(Universite u) { return universiteRepository.save(u); }

    @Override
    public Universite retrieveUniversite(long idUniversite) { return universiteRepository.findById(idUniversite).orElse(null); }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (foyer == null || universite == null) return null;
        // Link both sides
        foyer.setUniversite(universite);
        universite.setFoyer(foyer);
        foyerRepository.save(foyer); // owner side persists the relation
        return universiteRepository.findById(universite.getIdUniversite()).orElse(universite);
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite == null) return null;
        Foyer foyer = universite.getFoyer();
        if (foyer != null) {
            // Break association on both sides
            foyer.setUniversite(null);
            universite.setFoyer(null);
            foyerRepository.save(foyer); // update owning side
            universiteRepository.save(universite);
        }
        return universite;
    }
}
