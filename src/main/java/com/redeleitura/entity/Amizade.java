package com.redeleitura.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "amizades")
@Data
@IdClass(AmizadeId.class)
public class Amizade {

    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private Usuario amigo;

    @Column(name = "data_amizade")
    private LocalDateTime dataAmizade = LocalDateTime.now();
}

