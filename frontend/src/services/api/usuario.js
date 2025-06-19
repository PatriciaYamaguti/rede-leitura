const BASE_URL = 'http://localhost:8080/api/usuarios';

export async function cadastrarUsuario(dadosUsuario) {
    if (dadosUsuario.nome.length > 80) {
        return { sucesso: false, mensagem: "Nome com mais de 80 caracteres!" };
    }
    if (dadosUsuario.usuario.length > 20) {
        return { sucesso: false, mensagem: "Nome de usuário com mais de 20 caracteres!" };
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
            localStorage.setItem("id_usuario", data.idUsuario);
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