package com.redeleitura.dto;

import java.time.LocalDateTime;

import com.redeleitura.enums.StatusAmizade;

public class AmizadeLogDTO {
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idAmizade;
    private String descricao;
    private StatusAmizade status;
    private LocalDateTime dataHora;

    public AmizadeLogDTO() {}

    public AmizadeLogDTO(Integer idUsuario, String nomeUsuario, Integer idAmizade, String descricao, StatusAmizade status, LocalDateTime dataHora) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.idAmizade = idAmizade;
        this.descricao = descricao;
        this.status = status;
        this.dataHora = dataHora;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusAmizade getStatus() {
        return status;
    }

    public void setStatus(StatusAmizade status) {
        this.status = status;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Integer getIdAmizade() {
        return idAmizade;
    }

    public void setIdAmizade(Integer idAmizade) {
        this.idAmizade = idAmizade;
    }
}
