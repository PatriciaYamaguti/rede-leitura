import { useState } from "react";
import { Link } from "react-router-dom";
import { logarUsuario } from "../services/api/usuario";

const LoginForm = () => {
    const [usuario, setUsuario] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const usuarioLogin = { usuario, acesso: { senha } };

        const resultado = await logarUsuario(usuarioLogin);

        if(resultado.sucesso) {
            alert(resultado.mensagem)
            // navigate to...
        } else {
            alert(resultado.mensagem)
        }
    }

    return (
        <div className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md">
            <form onSubmit={handleSubmit} className="space-y-4">
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
                        Senha
                    </label>
                    <input
                        type="password"
                        placeholder="Digite sua senha"
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-[#978074] text-white py-2 rounded-lg hover:bg-[#806c62] transition duration-300 cursor-pointer"
                >
                    Entrar
                </button>
                <Link to="/cadastrar" className="text-[#757575] underline">Criar nova conta</Link>
            </form>
        </div>
    )
}

export default LoginForm;