package com.redeleitura.entity;

import java.io.Serializable;
import java.util.Objects;

public class AmizadeId implements Serializable {

    private Integer usuario;
    private Integer amigo;

    public AmizadeId() {}

    public AmizadeId(Integer usuario, Integer amigo) {
        this.usuario = usuario;
        this.amigo = amigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmizadeId)) return false;
        AmizadeId that = (AmizadeId) o;
        return Objects.equals(usuario, that.usuario) &&
               Objects.equals(amigo, that.amigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, amigo);
    }
}

