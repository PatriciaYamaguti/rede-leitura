package com.redeleitura.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class GoogleBooksUtil {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
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

    public record LivroDTO(String isbn, String titulo, String autor) {}
}
