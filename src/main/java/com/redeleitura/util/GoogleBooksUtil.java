package com.redeleitura.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GoogleBooksUtil {

    private final RestTemplate restTemplate = new RestTemplate();

    // Metodo para buscar livro por ISBN mantido
    @SuppressWarnings("unchecked") // unchecked ignora o tipo de retorno da API por exemplo em casos nulos
    public LivroDTO buscarLivroPorIsbn(String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("items")) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
            if (!items.isEmpty()) {
                Map<String, Object> item = items.get(0);
                Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

                String titulo = (String) volumeInfo.get("title");

                List<String> authors = (List<String>) volumeInfo.get("authors");
                String autor = (authors != null && !authors.isEmpty()) ? authors.get(0) : "Autor desconhecido";

                return new LivroDTO(isbn, titulo, autor);
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro não encontrado na API Google Books");
    }

    // Método para buscar livro por titulo. 
    @SuppressWarnings("unchecked")
    public List<LivroDTO> buscarLivrosPorTitulo(String termo) {
    String url = "https://www.googleapis.com/books/v1/volumes?q=" + termo + "&maxResults=10";
    Map<String, Object> response = restTemplate.getForObject(url, Map.class);

    List<LivroDTO> resultadosTitulo = new ArrayList<>();
    List<LivroDTO> resultadosAutor = new ArrayList<>();

    if (response != null && response.containsKey("items")) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
        String termoLower = termo.toLowerCase();

            for (Map<String, Object> item : items) {
            Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

            if (volumeInfo == null) continue; // Se volumeInfo é nulo, pula para o próximo

            String tituloLivro = (String) volumeInfo.get("title");
            List<String> authors = (List<String>) volumeInfo.get("authors");

            //Filtrar apenas livros com autor
            if (authors == null || authors.isEmpty()) continue;

            String autor = authors.get(0);

            // Filtrar apenas livros com ISBN
            String isbn = null;
            List<Map<String, String>> industryIdentifiers = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
            if (industryIdentifiers != null && !industryIdentifiers.isEmpty()) {
            isbn = industryIdentifiers.get(0).get("identifier");
            }
            if (isbn == null || isbn.trim().isEmpty()) continue;
            LivroDTO livro = new LivroDTO(isbn, tituloLivro, autor);

            if (tituloLivro != null && tituloLivro.toLowerCase().contains(termoLower)) {
                resultadosTitulo.add(livro); 
            } else if (autor.toLowerCase().contains(termoLower)) {
                resultadosAutor.add(livro); 
            }

            if (resultadosTitulo.size() >= 5) break; // Limitar a 5 resultados
            }
        }

            if (!resultadosTitulo.isEmpty()) {
                return resultadosTitulo;
            } else if (!resultadosAutor.isEmpty()) {
                return resultadosAutor.stream().limit(5).toList();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum livro encontrado com autor e ISBN válidos.");
            }
        }
        
    public record LivroDTO(String isbn, String titulo, String autor) {}
}
