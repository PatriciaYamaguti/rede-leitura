package com.redeleitura.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes_amizade", uniqueConstraints = @UniqueConstraint(columnNames = {"solicitante_id", "solicitado_id"}))
@Data
public class SolicitacaoAmizade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "solicitado_id", nullable = false)
    private Usuario solicitado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    @Column(name = "data_solicitacao")
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    public enum StatusSolicitacao {
        PENDENTE,
        ACEITA,
        RECUSADA
    }
}

