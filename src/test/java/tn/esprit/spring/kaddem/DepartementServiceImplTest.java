package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        departement = new Departement(1, "Informatique");
        etudiant = new Etudiant(1, "John", "Doe", null); // Exemple avec des valeurs minimales
        etudiant.setDepartement(departement); // Associer l'étudiant au département
        departement.setEtudiants(new HashSet<>(Arrays.asList(etudiant))); // Initialiser la collection
    }

    @Test
    void testRetrieveAllDepartements() {
        // Arrange
        List<Departement> departementList = Arrays.asList(
                new Departement(1, "Informatique"),
                new Departement(2, "Mathématiques")
        );
        when(departementRepository.findAll()).thenReturn(departementList);

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getNomDepart());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testAddDepartement() {
        // Arrange
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        // Act
        Departement result = departementService.addDepartement(departement);

        // Assert
        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testRetrieveDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        Departement result = departementService.retrieveDepartement(1);

        // Assert
        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        doNothing().when(departementRepository).delete(departement);

        // Act
        departementService.deleteDepartement(1);

        // Assert
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(departement);
    }

    @Test
    void testRetrieveEtudiantsByDepartement() {
        // Arrange
        Set<Etudiant> etudiants = new HashSet<>(Arrays.asList(etudiant));
        departement.setEtudiants(etudiants);
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        Departement result = departementService.retrieveDepartement(1);
        Set<Etudiant> retrievedEtudiants = result.getEtudiants();

        // Assert
        assertNotNull(retrievedEtudiants);
        assertEquals(1, retrievedEtudiants.size());
        assertTrue(retrievedEtudiants.contains(etudiant));
        verify(departementRepository, times(1)).findById(1);
    }
}