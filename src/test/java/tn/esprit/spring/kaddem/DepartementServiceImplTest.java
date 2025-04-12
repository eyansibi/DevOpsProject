package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) //pour indiquer que nous allons travailler avec des tests unitaires en utilisant JUnit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class DepartementServiceImplTest {


    @Autowired
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;



    @Test
    @Order(1)
    public void testRetrieveAllDepartements() {
        // Prépare des données fictives
        Departement d1 = new Departement();
        d1.setIdDepart(1);
        d1.setNomDepart("Informatique");
        Departement d2 = new Departement();
        d2.setIdDepart(2);
        d2.setNomDepart("Mathématiques");

        // Simule le comportement du repository
        when(departementRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        // Exécute la méthode
        List<Departement> departements = departementService.retrieveAllDepartements();

        // Vérifications
        assertEquals(2, departements.size());
        assertTrue(departements.contains(d1));
        assertTrue(departements.contains(d2));
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testAddDepartement() {
        // Prépare une entité fictive
        Departement d = new Departement();
        d.setIdDepart(1);
        d.setNomDepart("Informatique");

        // Simule le comportement
        when(departementRepository.save(d)).thenReturn(d);

        // Exécute la méthode
        Departement result = departementService.addDepartement(d);

        // Vérifications
        assertNotNull(result);
        assertEquals(d.getNomDepart(), result.getNomDepart());
        verify(departementRepository, times(1)).save(d);
    }

    @Test
    @Order(3)
    public void testRetrieveDepartement() {
        // Prépare une entité fictive
        Departement d = new Departement();
        d.setIdDepart(1);
        d.setNomDepart("Informatique");

        // Simule le comportement
        when(departementRepository.findById(1)).thenReturn(Optional.of(d));

        // Exécute la méthode
        Departement result = departementService.retrieveDepartement(1);

        // Vérifications
        assertNotNull(result);
        assertEquals(d.getNomDepart(), result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    @Order(4)
    public void testDeleteDepartement() {
        // Prépare une entité fictive
        Departement d = new Departement();
        d.setIdDepart(1);
        d.setNomDepart("Informatique");

        // Simule le comportement
        when(departementRepository.findById(1)).thenReturn(Optional.of(d));

        // Exécute la méthode
        departementService.deleteDepartement(1);

        // Vérifications
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(d);
    }
}