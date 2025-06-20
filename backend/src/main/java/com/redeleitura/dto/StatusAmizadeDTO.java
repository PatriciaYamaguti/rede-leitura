package com.redeleitura.dto;

public class StatusAmizadeDTO {
    private boolean existeAmizade;
    private String status;
    private Integer idAmizade;
    private Integer idSolicitante;

    public StatusAmizadeDTO(boolean existeAmizade, String status, Integer idAmizade, Integer idSolicitante) {
        this.existeAmizade = existeAmizade;
        this.status = status;
        this.idAmizade = idAmizade;
        this.idSolicitante = idSolicitante;
    }

    // Getters e setters
    public boolean isExisteAmizade() {
        return existeAmizade;
    }

    public void setExisteAmizade(boolean existeAmizade) {
        this.existeAmizade = existeAmizade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdAmizade() {
        return idAmizade;
    }

    public void setIdAmizade(Integer idAmizade) {
        this.idAmizade = idAmizade;
    }

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
}
