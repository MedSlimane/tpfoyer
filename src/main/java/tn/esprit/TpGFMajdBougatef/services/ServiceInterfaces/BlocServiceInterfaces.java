package tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces;

import tn.esprit.TpGFMajdBougatef.entities.Bloc;

import java.util.List;

public interface BlocServiceInterfaces {
    List<Bloc> retrieveBlocs();
    Bloc updateBloc(Bloc bloc);
    Bloc addBloc(Bloc bloc);
    Bloc retrieveBloc(long idBloc);
    void removeBloc(long idBloc);
    // Affecter une liste de chambres (par leurs numéros) à un bloc
    Bloc affecterChambresABloc(List<Long> numChambre, long idBloc);
}
