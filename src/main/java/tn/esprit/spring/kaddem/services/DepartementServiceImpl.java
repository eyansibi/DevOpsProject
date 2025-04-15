package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DepartementServiceImpl implements IDepartementService {

	private static final String DEPARTEMENT_NOT_FOUND_MESSAGE = "Departement not found with id: ";

	DepartementRepository departementRepository;

	@Override
	public List<Departement> retrieveAllDepartements() {


		return StreamSupport.stream(departementRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Departement retrieveDepartement(Integer id) {
		return findDepartementById(id);
	}

	@Override
	public DepartementDTO addDepartement(DepartementDTO departementDTO) {
		validateDepartementDTO(departementDTO);
		Departement departement = new Departement();
		departement.setNomDepart(departementDTO.getNomDepart());
		Departement saved = departementRepository.save(departement);
		return new DepartementDTO(saved.getIdDepart(), saved.getNomDepart());
	}

	@Override
	public void deleteDepartement(Integer id) {
		Departement departement = findDepartementById(id);
		departementRepository.delete(departement);
	}

	@Override
	public DepartementDTO updateDepartement(DepartementDTO departementDTO) {
		validateDepartementDTO(departementDTO);
		Departement departement = findDepartementById(departementDTO.getIdDepart());
		departement.setNomDepart(departementDTO.getNomDepart());
		Departement updated = departementRepository.save(departement);
		return new DepartementDTO(updated.getIdDepart(), updated.getNomDepart());
	}

	private void validateDepartementDTO(DepartementDTO departementDTO) {
		if (departementDTO == null) {
			throw new IllegalArgumentException("DepartementDTO cannot be null");
		}
	}

	private Departement findDepartementById(Integer id) {
		return departementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + id));
	}
}