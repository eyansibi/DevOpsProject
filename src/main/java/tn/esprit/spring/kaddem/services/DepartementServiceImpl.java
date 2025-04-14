package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartementServiceImpl implements IDepartementService {

	private static final String DEPARTEMENT_NOT_FOUND_MESSAGE = "Departement not found with id: ";

	DepartementRepository departementRepository;

	@Override
	public List<Departement> retrieveAllDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}

	@Override
	public Departement retrieveDepartement(Integer id) {
		return departementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + id));
	}

	@Override
	public DepartementDTO addDepartement(DepartementDTO d) {
		if (d == null) {
			throw new IllegalArgumentException("DepartementDTO cannot be null");
		}
		Departement departement = new Departement();
		departement.setNomDepart(d.getNomDepart());
		Departement saved = departementRepository.save(departement);
		return new DepartementDTO(saved.getIdDepart(), saved.getNomDepart());
	}

	@Override
	public void deleteDepartement(Integer id) {
		Departement departement = departementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + id));
		departementRepository.delete(departement);
	}

	@Override
	public DepartementDTO updateDepartement(DepartementDTO d) {
		if (d == null) {
			throw new IllegalArgumentException("DepartementDTO cannot be null");
		}
		Departement departement = departementRepository.findById(d.getIdDepart())
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + d.getIdDepart()));
		departement.setNomDepart(d.getNomDepart());
		Departement updated = departementRepository.save(departement);
		return new DepartementDTO(updated.getIdDepart(), updated.getNomDepart());
	}
}