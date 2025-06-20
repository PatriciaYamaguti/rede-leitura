const BASE_URL = 'http://localhost:8080/api/usuarios';

export async function cadastrarUsuario(dadosUsuario) {
    if (dadosUsuario.nome.length > 80) {
        return { sucesso: false, mensagem: "Nome com mais de 80 caracteres!" };
    }
    if (dadosUsuario.usuario.length > 20) {
        return { sucesso: false, mensagem: "Nome de usuário com mais de 20 caracteres!" };
    }

    if (dadosUsuario.descricao.length < 10) {
        return { sucesso: false, mensagem: "A descrição tem que ter mais de 10 caracteres!" };
    }

    if (dadosUsuario.acesso.senha.length < 3) {
        return { sucesso: false, mensagem: "Senha precisa ter mais que 3 caracteres!" };
    }

    try {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosUsuario)
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Cadastro realizado com sucesso!" };
        } else if (response.status === 409) {
            return { sucesso: false, mensagem: "Usuário já cadastrado!" };
        } else if (response.status === 400) {
            return { sucesso: false, mensagem: "Dados inválidos! Verifique as informações enviadas." };
        } else {
            return { sucesso: false, mensagem: "Algo deu errado, tente novamente mais tarde!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function logarUsuario(dadosLogin) {
    try {
        const response = await fetch(`${BASE_URL}/logar`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosLogin)
        });

        if (response.ok) {
            const data = await response.json();
            sessionStorage.setItem("idUsuario", data.idUsuario);
            return { sucesso: true, mensagem: "Login realizado com sucesso!" };
        } else if (response.status === 409) {
            return { sucesso: false, mensagem: "Nome de usuário não existente!" };
        } else if (response.status === 404) {
            return { sucesso: false, mensagem: "Senha incorreta!" };
        } else {
            return { sucesso: false, mensagem: "Erro desconhecido ao tentar fazer login!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function atualizarUsuario(id, dadosAtualizados) {
    if (dadosAtualizados.nome.length > 80) {
        return { sucesso: false, mensagem: "Nome com mais de 80 caracteres!" };
    }
    if (dadosAtualizados.usuario.length > 20) {
        return { sucesso: false, mensagem: "Nome de usuário com mais de 20 caracteres!" };
    }

    if (dadosAtualizados.descricao.length < 10) {
        return { sucesso: false, mensagem: "A descrição tem que ter mais de 10 caracteres!" };
    }
    if (dadosAtualizados.acesso.senha.length < 3) {
        return { sucesso: false, mensagem: "Senha precisa ter mais que 3 caracteres!" };
    }

    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosAtualizados)
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Usuário atualizado com sucesso!" };
        } else if (response.status === 404) {
            return { sucesso: false, mensagem: "Usuário não encontrado!" };
        } else if (response.status === 409) {
            return { sucesso: false, mensagem: "Já existe outro usuário com esse nome de usuário!" };
        } else {
            return { sucesso: false, mensagem: "Erro ao atualizar usuário. Tente novamente mais tarde!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function deletarUsuario(id) {
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Usuário deletado com sucesso!" };
        } else if (response.status === 404) {
            return { sucesso: false, mensagem: "Usuário não encontrado!" };
        } else {
            return { sucesso: false, mensagem: "Erro ao deletar usuário. Tente novamente mais tarde!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function listarUsuariosPorInteresses(idUsuario) {
    try {
        const response = await fetch(`${BASE_URL}/interesses/${idUsuario}`);

        if (response.ok) {
            const listaUsuarios = await response.json();
            return { sucesso: true, usuarios: listaUsuarios };
        } else {
            return { sucesso: false, mensagem: "Erro ao buscar usuários por interesses!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function buscarUsuarioPorId( idUsuario ) {
    try {
        const response = await fetch(`${BASE_URL}?idUsuario=${idUsuario}`)

        if (response.ok) {
            const usuario = await response.json();
            return { sucesso: true, usuario:  usuario}
        } else {
            return { sucesso: false, mensagem: "Erro ao buscar informações do usuário." }
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!"}
    }
}