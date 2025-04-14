package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DepartementServiceImpl implements IDepartementService {

	private static final String DEPARTEMENT_NOT_FOUND_MESSAGE = "Departement not found with id: ";

	DepartementRepository departementRepository;

	/**
	 * Retrieves all departments from the database.
	 *
	 * @return a list of all departments
	 */
	@Override
	public List<Departement> retrieveAllDepartements() {
		return StreamSupport.stream(departementRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves a department by its ID.
	 *
	 * @param id the ID of the department to retrieve
	 * @return the department with the specified ID
	 * @throws RuntimeException if no department is found with the given ID
	 */
	@Override
	public Departement retrieveDepartement(Integer id) {
		return departementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + id));
	}

	/**
	 * Adds a new department to the database.
	 *
	 * @param departementDTO the DTO containing the department details
	 * @return a DTO representing the saved department
	 * @throws IllegalArgumentException if the DTO is null
	 */
	@Override
	public DepartementDTO addDepartement(DepartementDTO departementDTO) {
		validateDepartementDTO(departementDTO);
		Departement departement = new Departement();
		departement.setNomDepart(departementDTO.getNomDepart());
		Departement saved = departementRepository.save(departement);
		return new DepartementDTO(saved.getIdDepart(), saved.getNomDepart());
	}

	/**
	 * Deletes a department by its ID.
	 *
	 * @param id the ID of the department to delete
	 * @throws RuntimeException if no department is found with the given ID
	 */
	@Override
	public void deleteDepartement(Integer id) {
		Departement departement = departementRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + id));
		departementRepository.delete(departement);
	}

	/**
	 * Updates an existing department.
	 *
	 * @param departementDTO the DTO containing the updated department details
	 * @return a DTO representing the updated department
	 * @throws IllegalArgumentException if the DTO is null
	 * @throws RuntimeException if no department is found with the given ID
	 */
	@Override
	public DepartementDTO updateDepartement(DepartementDTO departementDTO) {
		validateDepartementDTO(departementDTO);
		Departement departement = departementRepository.findById(departementDTO.getIdDepart())
				.orElseThrow(() -> new RuntimeException(DEPARTEMENT_NOT_FOUND_MESSAGE + departementDTO.getIdDepart()));
		departement.setNomDepart(departementDTO.getNomDepart());
		Departement updated = departementRepository.save(departement);
		return new DepartementDTO(updated.getIdDepart(), updated.getNomDepart());
	}

	/**
	 * Validates the provided DepartementDTO.
	 *
	 * @param departementDTO the DTO to validate
	 * @throws IllegalArgumentException if the DTO is null
	 */
	private void validateDepartementDTO(DepartementDTO departementDTO) {
		if (departementDTO == null) {
			throw new IllegalArgumentException("DepartementDTO cannot be null");
		}
	}
}