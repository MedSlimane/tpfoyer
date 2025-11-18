package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;
import tn.esprit.TpGFMajdBougatef.entities.Foyer;
import tn.esprit.TpGFMajdBougatef.entities.Universite;
import tn.esprit.TpGFMajdBougatef.repositories.FoyerRepository;
import tn.esprit.TpGFMajdBougatef.repositories.UniversiteRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.FoyerServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoyerServiceImp implements FoyerServiceInterfaces {

    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() { return foyerRepository.findAll(); }

    @Override
    public Foyer addFoyer(Foyer f) { return foyerRepository.save(f); }

    @Override
    public Foyer updateFoyer(Foyer f) { return foyerRepository.save(f); }

    @Override
    public Foyer retrieveFoyer(long idFoyer) { return foyerRepository.findById(idFoyer).orElse(null); }

    @Override
    public void removeFoyer(long idFoyer) { foyerRepository.deleteById(idFoyer); }

    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        // Récupérer l'université par son ID
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université non trouvée"));
        // Établir la relation bidirectionnelle entre les blocs et le foyer
        if (foyer.getBlocs() != null) {
            for (Bloc bloc : foyer.getBlocs()) {
                bloc.setFoyer(foyer);
            }
        }
        // Affecter l'université au foyer
        foyer.setUniversite(universite);
        // Sauvegarder le foyer (cascade sauvegarde automatiquement les blocs)
        return foyerRepository.save(foyer);
    }


}
