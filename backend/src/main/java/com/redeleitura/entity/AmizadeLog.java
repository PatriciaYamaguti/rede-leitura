package com.redeleitura.entity;

import com.redeleitura.enums.StatusAmizade;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class AmizadeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "amizade_id", nullable = false)
    private Amizade amizade;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column
    private String descricao;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusAmizade status;

    @Column
    private boolean ativa;

    @Column
    private LocalDateTime dataHora;

    public AmizadeLog() {}

    public AmizadeLog(Amizade amizade, Usuario usuario, StatusAmizade status, String descricao, boolean ativa, LocalDateTime dataHora) {
        this.amizade = amizade;
        this.usuario = usuario;
        this.status = status;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.ativa = ativa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmizade(Amizade amizade) {
        this.amizade = amizade;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(StatusAmizade status) {
        this.status = status;
    }
}
