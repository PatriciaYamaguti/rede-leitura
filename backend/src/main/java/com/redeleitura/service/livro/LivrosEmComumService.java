package com.redeleitura.service.livro;

import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.LivrosLidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivrosEmComumService {

    @Autowired
    private LivrosLidosRepository livrosLidosRepository;

    public Map<Usuario, Long> calcularLivrosEmComum(Usuario usuarioBase, List<Usuario> usuariosParaComparar) {
        List<LivrosLidos> livrosUsuarioBase = livrosLidosRepository.findByUsuario(usuarioBase);
        Set<String> isbnsBase = livrosUsuarioBase.stream()
                .map(LivrosLidos::getIsbn)
                .collect(Collectors.toSet());

        return getUsuarioLongMap(usuariosParaComparar, isbnsBase, livrosLidosRepository);
    }

    public static Map<Usuario, Long> getUsuarioLongMap(List<Usuario> usuariosParaComparar, Set<String> isbnsBase, LivrosLidosRepository livrosLidosRepository) {
        Map<Usuario, Long> resultado = new HashMap<>();

        for (Usuario outroUsuario : usuariosParaComparar) {
            List<LivrosLidos> livrosDoOutro = livrosLidosRepository.findByUsuario(outroUsuario);

            long quantidadeEmComum = livrosDoOutro.stream()
                    .filter(livro -> isbnsBase.contains(livro.getIsbn()))
                    .count();

            resultado.put(outroUsuario, quantidadeEmComum);
        }

        return resultado;
    }
}
