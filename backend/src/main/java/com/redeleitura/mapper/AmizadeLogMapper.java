package com.redeleitura.mapper;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.entity.AmizadeLog;
import com.redeleitura.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AmizadeLogMapper {

    public AmizadeLogDTO toDTO(AmizadeLog log, Usuario outroUsuario) {
        return new AmizadeLogDTO(
                outroUsuario.getId(),
                outroUsuario.getNome(),
                log.getAmizade() != null ? log.getAmizade().getId() : null,
                log.getDescricao(),
                log.getStatus(),
                log.getDataHora()
        );
    }
}
