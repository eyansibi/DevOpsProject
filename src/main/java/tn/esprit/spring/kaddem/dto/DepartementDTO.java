package tn.esprit.spring.kaddem.dto;

public class DepartementDTO {
    private Integer idDepart;
    private String nomDepart;

    public DepartementDTO() {
    }

    public DepartementDTO(Integer idDepart, String nomDepart) {
        this.idDepart = idDepart;
        this.nomDepart = nomDepart;
    }

    public Integer getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(Integer idDepart) {
        this.idDepart = idDepart;
    }

    public String getNomDepart() {
        return nomDepart;
    }

    public void setNomDepart(String nomDepart) {
        this.nomDepart = nomDepart;
    }
}