import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { atualizarUsuario, buscarUsuarioPorId, cadastrarUsuario } from "../services/api/usuario";
import { AlertContext } from "../contexts/AlertContext";

const CadastroAttForm = ( { tipo } ) => {
    const [nome, setNome] = useState('');
    const [usuario, setUsuario] = useState('');
    const [descricao, setDescricao] = useState('');
    const [senha, setSenha] = useState('');
    const { showAlert } = useContext(AlertContext);
    const idUsuario = sessionStorage.getItem("idUsuario");
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (tipo === "Atualização") {
            const preencherDados = async () => {
                setLoading(true);
                const resultado = await buscarUsuarioPorId(idUsuario);
                setLoading(false);

                if (resultado.sucesso) {
                    setNome(resultado.usuario.nome);
                    setUsuario(resultado.usuario.usuario);
                    setDescricao(resultado.usuario.descricao);
                } else {
                    showAlert(resultado.mensagem, "erro");
                }
            };

            preencherDados();
        }
    }, [tipo, idUsuario, showAlert]);

if (loading) return <p>Carregando dados...</p>;

 
    const handleSubmit = async (e) => {
        e.preventDefault();

        const novoUsuario = { nome, usuario, descricao, acesso: { tipoAcesso: "USER", senha } };

        if(tipo == "Cadastro") {
            const resultado = await cadastrarUsuario(novoUsuario);

            if (resultado.sucesso) {
                showAlert(resultado.mensagem, "sucesso");
                navigate("/logar");
            } else {
                showAlert(resultado.mensagem, "erro");
            }
        } else {
            const idUsuario = sessionStorage.getItem("idUsuario");
            const resultado = await atualizarUsuario(idUsuario, novoUsuario);

            if (resultado.sucesso) {
                showAlert(resultado.mensagem, "sucesso");
            } else {
                showAlert(resultado.mensagem, "erro");
            }
        }
    };

    return (
        <div className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md">
            <h2 className="text-2xl font-bold text-center text-[#525050] mb-6">
                { tipo } do Leitor
            </h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Nome
                    </label>
                    <input
                        type="text"
                        placeholder="Digite seu nome"
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Nome de Usuário
                    </label>
                    <input
                        type="text"
                        placeholder="Digite seu nome de usuário"
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
                        value={usuario}
                        onChange={(e) => setUsuario(e.target.value)}
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Descrição
                    </label>
                    <textarea
                        placeholder="Fale um pouco sobre seus livros favoritos..."
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
                        rows="3"
                        value={descricao}
                        onChange={(e) => setDescricao(e.target.value)}
                    ></textarea>
                </div>

                {tipo === "Cadastro" && (
                    <div>
                        <label className="block text-gray-700 font-medium mb-1">
                            Senha
                        </label>
                        <input
                            type="text"
                            placeholder="Digite sua senha aqui"
                            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                        />
                    </div>
                )}

                <button
                    type="submit"
                    className="w-full bg-[#978074] text-white py-2 rounded-lg hover:bg-[#806c62] transition duration-300 cursor-pointer"
                >
                    Enviar
                </button>

                {tipo === "Cadastro" && (
                    <Link to="/logar" className="text-[#757575] underline">
                        Já tem uma conta?
                    </Link>
                )}
            </form>
        </div>
    )
}

export default CadastroAttForm;