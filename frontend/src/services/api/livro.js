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

export async function definirLivroAtual(idUsuario, isbn) {
    try {
        const response = await fetch(`${BASE_URL}/${idUsuario}/atual/${isbn}`, {
            method: 'POST'
        });

        if (response.ok) {
            const data = await response.json();
            return { sucesso: true, mensagem: `${data.titulo} foi marcado como leitura atual com sucesso!` };
        } else if (response.status === 400) {
            return { sucesso: false, mensagem: "Usuário ou ISBN inválido." };
        } else {
            return { sucesso: false, mensagem: "Erro ao definir o livro atual." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function marcarLivroComoLido(idUsuario, isbn) {
    try {
        const response = await fetch(`${BASE_URL}/${idUsuario}/lido/${isbn}`, {
            method: 'POST'
        });

        if (response.ok) {
            const data = await response.json();
            return { sucesso: true, mensagem: `${data.titulo} foi marcado como livro lido com sucesso!` };
        } else if (response.status === 400) {
            return { sucesso: false, mensagem: "Usuário não encontrado ou ISBN inválido." };
        } else {
            return { sucesso: false, mensagem: "Erro ao marcar o livro como lido." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}