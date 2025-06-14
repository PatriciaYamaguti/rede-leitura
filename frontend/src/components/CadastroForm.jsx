import { Link } from "react-router-dom";

const CadastroForm = () => {
    return (
        <div className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md">
            <h2 className="text-2xl font-bold text-center text-[#4B6043] mb-6">
                Cadastro de Leitor
            </h2>
            <form className="space-y-4">
                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Nome
                    </label>
                    <input
                        type="text"
                        placeholder="Digite seu nome"
                        className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-1 focus:ring-[#978074]"
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
                    ></textarea>
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