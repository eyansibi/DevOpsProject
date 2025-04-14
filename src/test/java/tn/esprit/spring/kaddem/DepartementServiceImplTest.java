package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DepartementServiceUnitTest {

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    DepartementServiceImpl departementService;

    private Departement departement;
    private DepartementDTO departementDTO;

    @BeforeEach
    void setup() {
        departement = new Departement(1, "Informatique");
        departementDTO = new DepartementDTO(1, "Informatique");
    }

    @Test
    void testGetDepartementsList() {
        Departement departement1 = new Departement(1, "Informatique");
        Departement departement2 = new Departement(2, "Mathématiques");
        when(departementRepository.findAll()).thenReturn(Arrays.asList(departement1, departement2));
        List<Departement> departementList = departementService.retrieveAllDepartements();
        assertEquals(2, departementList.size());
        assertEquals("Informatique", departementList.get(0).getNomDepart());
        assertEquals("Mathématiques", departementList.get(1).getNomDepart());
    }

    @Test
    void testGetDepartementById() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        Departement departementById = departementService.retrieveDepartement(1);
        assertEquals("Informatique", departementById.getNomDepart());
    }

    @Test
    void testGetInvalidDepartementById() {
        when(departementRepository.findById(999)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departementService.retrieveDepartement(999);
        });
        assertTrue(exception.getMessage().contains("Departement not found with id: 999"));
    }

    @Test
    void testCreateDepartement() {
        Departement savedDepartement = new Departement(1, "Informatique");
        when(departementRepository.save(any(Departement.class))).thenReturn(savedDepartement);
        DepartementDTO result = departementService.addDepartement(departementDTO);
        verifyAndCaptureDepartement("Informatique", result);
    }

    @Test
    void testCreateDepartementWithNullShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            departementService.addDepartement(null);
        });
        assertTrue(exception.getMessage().contains("DepartementDTO cannot be null"));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartement() {
        DepartementDTO updatedDTO = new DepartementDTO(1, "Informatique Modifié");
        Departement existingDepartement = new Departement(1, "Informatique");
        Departement updatedDepartement = new Departement(1, "Informatique Modifié");
        when(departementRepository.findById(1)).thenReturn(Optional.of(existingDepartement));
        when(departementRepository.save(any(Departement.class))).thenReturn(updatedDepartement);
        DepartementDTO result = departementService.updateDepartement(updatedDTO);
        verifyAndCaptureDepartement("Informatique Modifié", result);
    }

    @Test
    void testUpdateDepartementWithInvalidId() {
        DepartementDTO invalidDTO = new DepartementDTO(999, "Informatique Modifié");
        when(departementRepository.findById(999)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departementService.updateDepartement(invalidDTO);
        });
        assertTrue(exception.getMessage().contains("Departement not found with id: 999"));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartementWithNullShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            departementService.updateDepartement(null);
        });
        assertTrue(exception.getMessage().contains("DepartementDTO cannot be null"));
        verify(departementRepository, never()).save(any(Departement.class));
    }

    @Test
    void testDeleteDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        departementService.deleteDepartement(departement.getIdDepart());
        verify(departementRepository, times(1)).delete(departement);
        ArgumentCaptor<Departement> departementArgumentCaptor = ArgumentCaptor.forClass(Departement.class);
        verify(departementRepository).delete(departementArgumentCaptor.capture());
        Departement deletedDepartement = departementArgumentCaptor.getValue();
        assertEquals(1, deletedDepartement.getIdDepart());
    }

    @Test
    void testDeleteDepartementWithInvalidId() {
        when(departementRepository.findById(999)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departementService.deleteDepartement(999);
        });
        assertTrue(exception.getMessage().contains("Departement not found with id: 999"));
        verify(departementRepository, never()).delete(any(Departement.class));
    }

    private void verifyAndCaptureDepartement(String expectedNomDepart, DepartementDTO result) {
        verify(departementRepository, times(1)).save(any(Departement.class));
        ArgumentCaptor<Departement> departementArgumentCaptor = ArgumentCaptor.forClass(Departement.class);
        verify(departementRepository).save(departementArgumentCaptor.capture());
        Departement departementCaptured = departementArgumentCaptor.getValue();
        assertEquals(expectedNomDepart, departementCaptured.getNomDepart());
        assertEquals(1, result.getIdDepart());
        assertEquals(expectedNomDepart, result.getNomDepart());
    }
}