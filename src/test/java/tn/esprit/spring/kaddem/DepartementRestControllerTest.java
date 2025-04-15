package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.kaddem.controllers.DepartementRestController;
import tn.esprit.spring.kaddem.dto.DepartementDTO;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.services.IDepartementService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartementRestController.class)
class DepartementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDepartementService departementService;

    @Test
    void testGetDepartements() throws Exception {
        // Arrange
        List<Departement> departements = Arrays.asList(
                new Departement(1, "Informatique"),
                new Departement(2, "Mathématiques")
        );
        when(departementService.retrieveAllDepartements()).thenReturn(departements);

        // Act & Assert
        mockMvc.perform(get("/departement/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDepart").value(1))
                .andExpect(jsonPath("$[0].nomDepart").value("Informatique"))
                .andExpect(jsonPath("$[1].idDepart").value(2))
                .andExpect(jsonPath("$[1].nomDepart").value("Mathématiques"));

        verify(departementService, times(1)).retrieveAllDepartements();
    }

    @Test
    void testRetrieveDepartement() throws Exception {
        // Arrange
        Departement departement = new Departement(1, "Informatique");
        when(departementService.retrieveDepartement(1)).thenReturn(departement);

        // Act & Assert
        mockMvc.perform(get("/departement/retrieve/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepart").value(1))
                .andExpect(jsonPath("$.nomDepart").value("Informatique"));

        verify(departementService, times(1)).retrieveDepartement(1);
    }

    @Test
    void testAddDepartement() throws Exception {
        // Arrange
        DepartementDTO departementDTO = new DepartementDTO(1, "Informatique");
        when(departementService.addDepartement(any(DepartementDTO.class))).thenReturn(departementDTO);

        // Act & Assert
        mockMvc.perform(post("/departement/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDepart\":1,\"nomDepart\":\"Informatique\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDepart").value(1))
                .andExpect(jsonPath("$.nomDepart").value("Informatique"));

        verify(departementService, times(1)).addDepartement(any(DepartementDTO.class));
    }

    @Test
    void testUpdateDepartement() throws Exception {
        // Arrange
        DepartementDTO departementDTO = new DepartementDTO(1, "Informatique Modifié");
        when(departementService.updateDepartement(any(DepartementDTO.class))).thenReturn(departementDTO);

        // Act & Assert
        mockMvc.perform(put("/departement/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idDepart\":1,\"nomDepart\":\"Informatique Modifié\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepart").value(1))
                .andExpect(jsonPath("$.nomDepart").value("Informatique Modifié"));

        verify(departementService, times(1)).updateDepartement(any(DepartementDTO.class));
    }

    @Test
    void testRemoveDepartement() throws Exception {
        // Arrange
        doNothing().when(departementService).deleteDepartement(1);

        // Act & Assert
        mockMvc.perform(delete("/departement/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(departementService, times(1)).deleteDepartement(1);
    }
}