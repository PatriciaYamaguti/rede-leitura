const BASE_URL = 'http://localhost:8000/api/amizade';

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

export async function buscarStatusAmizade(idUsuario1, idUsuario2) {
    try {
        const response = await fetch(`${BASE_URL}/status?idUsuario1=${idUsuario1}&idUsuario2=${idUsuario2}`);

        if (response.ok) {
            const data = await response.json();
            return data;
        } else {
            console.error("Erro ao buscar status da amizade. Status HTTP:", response.status);
            return { existeAmizade: false, status: null };
        }
    } catch (error) {
        console.error("Erro ao buscar status da amizade:", error);
        return { existeAmizade: false, status: null };
    }
}

export async function marcarComoLido(idUsuario1, idUsuario2) {
    try {
        const response = await fetch(`${BASE_URL}/marcarLido?idUsuario1=${idUsuario1}&idUsuario2=${idUsuario2}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Marcado como lido com sucesso." };
        } else if (response.status === 404) {
            const mensagem = await response.text();
            return { sucesso: false, mensagem: mensagem || "Amizade ou solicitação não encontrada." };
        } else {
            console.error("Erro inesperado ao marcar como lido. Status HTTP:", response.status);
            return { sucesso: false, mensagem: `Erro inesperado (${response.status})` };
        }
    } catch (error) {
        console.error("Erro ao marcar como lido:", error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}