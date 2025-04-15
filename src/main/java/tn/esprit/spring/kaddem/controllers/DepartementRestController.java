package tn.esprit.spring.kaddem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.services.IDepartementService;

import java.util.List;

@RestController
@RequestMapping("/departement")
public class DepartementRestController {

	private final IDepartementService departementService;

	public DepartementRestController(IDepartementService departementService) {
		this.departementService = departementService;
	}

	/*@GetMapping("/all")
	public ResponseEntity<List<Departement>> getDepartements() {
		List<Departement> listDepartements = departementService.retrieveAllDepartements();
		return new ResponseEntity<>(listDepartements, HttpStatus.OK);
	}*/

	@GetMapping("/retrieve/{id}")
	public ResponseEntity<Departement> retrieveDepartement(@PathVariable("id") Integer id) {
		Departement departement = departementService.retrieveDepartement(id);
		return new ResponseEntity<>(departement, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<DepartementDTO> addDepartement(@RequestBody DepartementDTO d) {
		DepartementDTO departementDTO = departementService.addDepartement(d);
		return new ResponseEntity<>(departementDTO, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<DepartementDTO> updateDepartement(@RequestBody DepartementDTO d) {
		DepartementDTO departementDTO = departementService.updateDepartement(d);
		return new ResponseEntity<>(departementDTO, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> removeDepartement(@PathVariable("id") Integer id) {
		departementService.deleteDepartement(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}