package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    private final DepartementRepository departementRepository = mock(DepartementRepository.class);
    private final DepartementServiceImpl departementService = new DepartementServiceImpl(departementRepository);
    private final Departement departement = new Departement(1, "Informatique");
    private final DepartementDTO departementDTO = new DepartementDTO(1, "Informatique");

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
        assertEquals(departementList, result);
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testAddDepartement() {
        // Arrange
        Departement savedDepartement = new Departement(1, "Informatique");
        when(departementRepository.save(any(Departement.class))).thenReturn(savedDepartement);

        // Act
        DepartementDTO result = departementService.addDepartement(departementDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertEquals("Informatique", result.getNomDepart());
        assertEquals(savedDepartement.getIdDepart(), result.getIdDepart());
        assertEquals(savedDepartement.getNomDepart(), result.getNomDepart());
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    void testAddDepartementWithNullName() {
        // Arrange
        DepartementDTO dtoWithNullName = new DepartementDTO(1, null);
        Departement savedDepartement = new Departement(1, null);
        when(departementRepository.save(any(Departement.class))).thenReturn(savedDepartement);

        // Act
        DepartementDTO result = departementService.addDepartement(dtoWithNullName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertNull(result.getNomDepart());
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    void testAddDepartementWithNullShouldFail() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departementService.addDepartement(null));
        verify(departementRepository, never()).save(any(Departement.class));
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
        assertEquals(departement, result);
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
    void testUpdateDepartement() {
        // Arrange
        DepartementDTO updatedDTO = new DepartementDTO(1, "Informatique Modifié");
        Departement existingDepartement = new Departement(1, "Informatique");
        Departement updatedDepartement = new Departement(1, "Informatique Modifié");
        when(departementRepository.findById(1)).thenReturn(Optional.of(existingDepartement));
        when(departementRepository.save(any(Departement.class))).thenReturn(updatedDepartement);

        // Act
        DepartementDTO result = departementService.updateDepartement(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertEquals("Informatique Modifié", result.getNomDepart());
        assertEquals(updatedDepartement.getIdDepart(), result.getIdDepart());
        assertEquals(updatedDepartement.getNomDepart(), result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementWithNullName() {
        // Arrange
        DepartementDTO updatedDTO = new DepartementDTO(1, null);
        Departement existingDepartement = new Departement(1, "Informatique");
        Departement updatedDepartement = new Departement(1, null);
        when(departementRepository.findById(1)).thenReturn(Optional.of(existingDepartement));
        when(departementRepository.save(any(Departement.class))).thenReturn(updatedDepartement);

        // Act
        DepartementDTO result = departementService.updateDepartement(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertNull(result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).save(any(Departement.class));
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

    @Test
    void testUpdateDepartementWithInvalidId() {
        // Arrange
        DepartementDTO invalidDTO = new DepartementDTO(999, "Informatique Modifié");
        when(departementRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departementService.updateDepartement(invalidDTO));
        verify(departementRepository, times(1)).findById(999);
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementWithNullShouldFail() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departementService.updateDepartement(null));
        verify(departementRepository, never()).findById(anyInt());
        verify(departementRepository, never()).save(any(Departement.class));
    }
}