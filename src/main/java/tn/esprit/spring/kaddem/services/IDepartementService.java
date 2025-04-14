package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;

import java.util.List;

public interface IDepartementService {
    List<Departement> retrieveAllDepartements();
    Departement retrieveDepartement(Integer id);
    DepartementDTO addDepartement(DepartementDTO d);
    void deleteDepartement(Integer id);
    DepartementDTO updateDepartement(DepartementDTO d);
}
