const BASE_URL = 'http://localhost:8080/api/amizade';

export async function enviarSolicitacaoAmizade(idSolicitante, idSolicitado) {
    try {
        const response = await fetch(`${BASE_URL}/solicitar?idSolicitante=${idSolicitante}&idSolicitado=${idSolicitado}`, {
            method: 'POST'
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Solicitação de amizade enviada com sucesso!" };
        } else if (response.status === 409) {
            return { sucesso: false, mensagem: "Já existe uma solicitação ou amizade entre esses usuários." };
        } else {
            return { sucesso: false, mensagem: "Erro ao enviar solicitação de amizade." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function aceitarSolicitacaoAmizade(idSolicitacao) {
    try {
        const response = await fetch(`${BASE_URL}/aceitar?idSolicitacao=${idSolicitacao}`, {
            method: 'POST'
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Solicitação de amizade aceita!" };
        } else if (response.status === 409) {
            return { sucesso: false, mensagem: "Os usuários já são amigos ou não há uma solicitação pendente." };
        } else {
            return { sucesso: false, mensagem: "Erro ao aceitar a solicitação de amizade." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function recusarSolicitacaoAmizade(idSolicitacao) {
    try {
        const response = await fetch(`${BASE_URL}/recusar?idSolicitacao=${idSolicitacao}`, {
            method: 'POST'
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Solicitação ou amizade removida com sucesso!" };
        } else if (response.status === 404) {
            return { sucesso: false, mensagem: "Não há amizade nem solicitação pendente entre os usuários." };
        } else {
            return { sucesso: false, mensagem: "Erro ao recusar/remover a solicitação de amizade." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function listarAmigos(idUsuario) {
    try {
        const response = await fetch(`${BASE_URL}/listar?idUsuario=${idUsuario}`, {
            method: 'POST'
        });

        if (response.ok) {
            const amigos = await response.json();
            return { sucesso: true, amigos: amigos };
        } else {
            return { sucesso: false, mensagem: "Erro ao listar amigos." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}

export async function listarAmizadeLog(idUsuario) {
    try {
        const response = await fetch(`${BASE_URL}?idUsuario=${idUsuario}`);

        if (response.ok) {
            const logs = await response.json();
            return { sucesso: true, logs: logs };
        } else {
            return { sucesso: false, mensagem: "Erro ao listar o log de amizades." };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}