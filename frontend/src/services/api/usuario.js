export async function cadastrarUsuario(dadosUsuario) {
    if(dadosUsuario.nome.length > 80) { return {sucesso: false, mensagem: "Nome com mais de 80 caracteres!"} }
    if(dadosUsuario.usuario.length > 20) { return {sucesso: false, mensagem: "Nome de usuário com mais de 20 caracteres!"} }
    
    try {
        const response = await fetch('http://localhost:8080/api/usuarios', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosUsuario)
        });

        if (response.ok) {
            return { sucesso: true, mensagem: "Cadastro realizado com sucesso!" };
        } else if (response.status === 400) {
            return { sucesso: false, mensagem: "Usuário já cadastrado!" };
        } else {
            return { sucesso: false, mensagem: "Algo deu errado, tente novamente mais tarde!" };
        }
    } catch (error) {
        console.error(error);
        return { sucesso: false, mensagem: "Falha de conexão com o servidor!" };
    }
}
