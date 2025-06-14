import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { cadastrarUsuario } from "../services/api/usuario";

const CadastroForm = () => {
    const [nome, setNome] = useState('');
    const [usuario, setUsuario] = useState('');
    const [descricao, setDescricao] = useState('');
    const [senha, setSenha] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const novoUsuario = { nome, usuario, descricao, acesso: { tipoAcesso: "USER", senha } };

        const resultado = await cadastrarUsuario(novoUsuario);

        if (resultado.sucesso) {
            alert(resultado.mensagem)
            navigate("/logar");
        } else {
            alert(resultado.mensagem);
        }
    };

    return (
        <div className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md">
            <h2 className="text-2xl font-bold text-center text-[#4B6043] mb-6">
                Cadastro de Leitor
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

                <button
                    type="submit"
                    className="w-full bg-[#978074] text-white py-2 rounded-lg hover:bg-[#806c62] transition duration-300 cursor-pointer"
                >
                    Cadastrar
                </button>

                <Link to="/logar" className="text-[#757575] underline">Já tem uma conta?</Link>
            </form>
        </div>
    )
}

export default CadastroForm;