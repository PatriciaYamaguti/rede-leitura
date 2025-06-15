package com.redeleitura.mapper;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class UsuarioLivrosEmComumMapper {

    public List<UsuarioLivrosEmComumDTO> toDTOList(Map<Usuario, Long> usuariosComLivrosEmComum, boolean ordenarPorQuantidade) {
        Stream<Map.Entry<Usuario, Long>> stream = usuariosComLivrosEmComum.entrySet().stream();

        if (ordenarPorQuantidade) {
            stream = stream.sorted(Map.Entry.<Usuario, Long>comparingByValue(Comparator.reverseOrder()));
        }

        return stream
                .map(entry -> {
                    Usuario usuario = entry.getKey();
                    Long quantidade = entry.getValue();
                    return new UsuarioLivrosEmComumDTO(
                            quantidade,
                            usuario.getNome(),
                            usuario.getDescricao(),
                            usuario.getUsuario(),
                            usuario.getId()
                    );
                })
                .toList();
    }
}
