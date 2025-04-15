package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    private Departement departement;
    private List<Departement> departementList;
    private DepartementDTO departementDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");

        departementList = Arrays.asList(departement);

        departementDTO = new DepartementDTO();
        departementDTO.setIdDepart(1);
        departementDTO.setNomDepart("Informatique");
    }

    @Test
    void testRetrieveAllDepartements() {
        // Arrange
        when(departementRepository.findAll()).thenReturn(departementList);

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Informatique", result.get(0).getNomDepart());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveAllDepartementsEmptyList() {
        // Arrange
        when(departementRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        // Assert
        assertTrue(result.isEmpty());
        verify(departementRepository, times(1)).findAll();
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
    void testRetrieveDepartementWithInvalidId() {
        // Arrange
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departementService.retrieveDepartement(999));
        verify(departementRepository, times(1)).findById(999);
    }

    @Test
    void testAddDepartement() {
        // Arrange
        Departement savedDepartement = new Departement();
        savedDepartement.setIdDepart(1);
        savedDepartement.setNomDepart("Informatique");
        when(departementRepository.save(any(Departement.class))).thenReturn(savedDepartement);

        // Act
        DepartementDTO result = departementService.addDepartement(departementDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        assertEquals(1, result.getIdDepart());
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    void testAddDepartementWithNullDTO() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departementService.addDepartement(null));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartement() {
        // Arrange
        Departement existingDepartement = new Departement();
        existingDepartement.setIdDepart(1);
        existingDepartement.setNomDepart("Informatique");
        when(departementRepository.findById(1)).thenReturn(Optional.of(existingDepartement));
        when(departementRepository.save(any(Departement.class))).thenReturn(existingDepartement);

        // Act
        DepartementDTO result = departementService.updateDepartement(departementDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        assertEquals(1, result.getIdDepart());
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementWithNullDTO() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departementService.updateDepartement(null));
        verify(departementRepository, never()).findById(anyInt());
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementWithInvalidId() {
        // Arrange
        DepartementDTO invalidDTO = new DepartementDTO();
        invalidDTO.setIdDepart(999);
        invalidDTO.setNomDepart("Informatique");
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departementService.updateDepartement(invalidDTO));
        verify(departementRepository, times(1)).findById(999);
        verify(departementRepository, never()).save(any(Departement.class));
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
    void testDeleteDepartementWithInvalidId() {
        // Arrange
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departementService.deleteDepartement(999));
        verify(departementRepository, times(1)).findById(999);
        verify(departementRepository, never()).delete(any(Departement.class));
    }
}