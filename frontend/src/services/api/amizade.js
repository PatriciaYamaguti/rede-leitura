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