const BASE_URL = 'http://localhost:8080/api/livros';

export async function buscarLivrosPorTitulo(titulo) {
    try {
        const response = await fetch(`${BASE_URL}/buscar?titulo=${encodeURIComponent(titulo)}`);

        if (response.ok) {
            const livros = await response.json();
            return { sucesso: true, livros: livros };
        } else {
            return { sucesso: false, mensagem: "Erro ao buscar livros pelo título." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}