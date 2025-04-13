package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.*;

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
        etudiant = new Etudiant(1, "John", "Doe", null);
        etudiant.setDepartement(departement);
        departement.setEtudiants(new HashSet<>(Arrays.asList(etudiant)));
    }

    @Test
    @Order(1)
    void testRetrieveAllDepartements() {
        List<Departement> departementList = Arrays.asList(
                new Departement(1, "Informatique"),
                new Departement(2, "Mathématiques")
        );
        when(departementRepository.findAll()).thenReturn(departementList);

        List<Departement> result = departementService.retrieveAllDepartements();

        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getNomDepart());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testRetrieveAllDepartementsWhenEmpty() {
        when(departementRepository.findAll()).thenReturn(Collections.emptyList());

        List<Departement> result = departementService.retrieveAllDepartements();

        assertTrue(result.isEmpty());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    @Order(3)
    void testAddDepartement() {
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.addDepartement(departement);

        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    @Order(4)
    void testAddDepartementWithNullShouldFail() {
        when(departementRepository.save(null)).thenThrow(new IllegalArgumentException("Departement cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> departementService.addDepartement(null));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    @Order(5)
    void testRetrieveDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        Departement result = departementService.retrieveDepartement(1);

        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    @Order(6)
    void testRetrieveDepartementWithInvalidId() {
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> departementService.retrieveDepartement(999));
        verify(departementRepository, times(1)).findById(999);
    }

    @Test
    @Order(7)
    void testUpdateDepartement() {
        departement.setNomDepart("Informatique Modifié");
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.updateDepartement(departement);

        assertNotNull(result);
        assertEquals("Informatique Modifié", result.getNomDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    @Order(8)
    void testUpdateDepartementWithNullShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> departementService.updateDepartement(null));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    @Order(9)
    void testDeleteDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        doNothing().when(departementRepository).delete(departement);

        departementService.deleteDepartement(1);

        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(departement);
    }

    @Test
    @Order(10)
    void testDeleteDepartementWithInvalidId() {
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> departementService.deleteDepartement(999));
        verify(departementRepository, times(1)).findById(999);
        verify(departementRepository, never()).delete(any(Departement.class));
    }

    @Test
    @Order(11)
    void testRetrieveEtudiantsByDepartement() {
        Set<Etudiant> etudiants = new HashSet<>(Arrays.asList(etudiant));
        departement.setEtudiants(etudiants);
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        Departement result = departementService.retrieveDepartement(1);
        Set<Etudiant> retrievedEtudiants = result.getEtudiants();

        assertNotNull(retrievedEtudiants);
        assertEquals(1, retrievedEtudiants.size());
        assertTrue(retrievedEtudiants.contains(etudiant));
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    @Order(12)
    void testDepartementEqualsAndHashCode() {
        Departement d1 = new Departement(1, "Info");
        Departement d2 = new Departement(1, "Info");

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    @Order(13)
    void testDepartementToString() {
        String toString = departement.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Informatique"));
    }
}
