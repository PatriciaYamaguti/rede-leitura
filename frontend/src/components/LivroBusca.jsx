import { useContext, useState } from "react";
import {
  buscarLivrosPorTitulo,
  definirLivroAtual,
  marcarLivroComoLido,
} from "../services/api/livro";
import { AlertContext } from "../contexts/AlertContext";
import { FiSearch, FiBook, FiBookOpen, FiX } from "react-icons/fi";

function LivroBusca({ idUsuario }) {
  const [titulo, setTitulo] = useState("");
  const [sugestoes, setSugestoes] = useState([]);
  const [selecionado, setSelecionado] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { showAlert } = useContext(AlertContext);

  const buscarLivros = async () => {
    if (!titulo.trim()) return;
    
    setIsLoading(true);
    setSelecionado(null);
    const resposta = await buscarLivrosPorTitulo(titulo);
    
    if (resposta.sucesso) {
      setSugestoes(resposta.livros);
    } else {
      setSugestoes([]);
      showAlert(resposta.mensagem, "erro");
    }
    setIsLoading(false);
  };

  const selecionarLivro = (livro) => {
    setSelecionado(livro);
    setSugestoes([]);
    setTitulo(livro.titulo);
  };

  const limparBusca = () => {
    setTitulo("");
    setSugestoes([]);
    setSelecionado(null);
  };

  const definirAtual = async () => {
    const resposta = await definirLivroAtual(idUsuario, selecionado.isbn);
    if(resposta.sucesso) {
      showAlert(resposta.mensagem, "sucesso");
      limparBusca();
    } else {
      showAlert(resposta.mensagem, "erro");
    }
  };

  const marcarLido = async () => {
    const resposta = await marcarLivroComoLido(idUsuario, selecionado.isbn);
    if(resposta.sucesso) {
      showAlert(resposta.mensagem, "sucesso");
      limparBusca();
    } else {
      showAlert(resposta.mensagem, "erro");
    }
  };

  return (
    <div className="w-2xl mx-auto">
      <div className="bg-white p-8 rounded-xl shadow-lg">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Adicione suas leituras
        </h2>

        <div className="relative">
          <div className="flex items-center mb-2">
            <div className="relative flex-grow">
              <input
                type="text"
                value={titulo}
                placeholder="Digite o título do livro..."
                onChange={(e) => {
                  setTitulo(e.target.value);
                  setSelecionado(null);
                }}
                onKeyDown={(e) => e.key === 'Enter' && buscarLivros()}
                className="w-full border border-gray-200 px-4 py-3 pr-10 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#8a6d5b] focus:border-transparent"
              />
              {titulo && (
                <button
                  onClick={limparBusca}
                  className="absolute right-4 top-4 text-gray-400 hover:text-gray-600"
                >
                  <FiX size={18} />
                </button>
              )}
            </div>
            <button
              onClick={buscarLivros}
              disabled={!titulo.trim() || isLoading}
              className="ml-2 bg-gradient-to-r from-[#8a6d5b] to-[#a58b79] text-white px-5 py-3 rounded-lg hover:opacity-90 transition flex items-center disabled:opacity-50 cursor-pointer"
            >
              {isLoading ? (
                <span className="animate-pulse">Buscando...</span>
              ) : (
                <>
                  <FiSearch className="mr-2" />
                  Buscar
                </>
              )}
            </button>
          </div>

          {sugestoes.length > 0 && !selecionado && (
            <ul className="absolute z-10 w-full mt-1 bg-white border border-gray-200 rounded-lg shadow-lg max-h-60 overflow-y-auto">
              {sugestoes.map((livro) => (
                <li
                  key={livro.isbn}
                  onClick={() => selecionarLivro(livro)}
                  className="px-4 py-3 cursor-pointer hover:bg-gray-50 transition border-b border-gray-100 last:border-b-0"
                >
                  <div className="font-medium text-gray-800">{livro.titulo}</div>
                  <div className="text-sm text-gray-500">por {livro.autor}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>

      {selecionado && (
        <div className="mt-6 p-6 rounded-xl bg-white shadow-lg border border-gray-100">
          <div className="flex flex-col md:flex-row md:items-center justify-between">
            <div className="mb-4 md:mb-0">
              <h3 className="text-xl font-semibold text-gray-800">{selecionado.titulo}</h3>
              <p className="text-gray-600 italic">por {selecionado.autor}</p>
            </div>
            
            <div className="flex flex-col sm:flex-row gap-3">
              <button
                onClick={definirAtual}
                className="flex items-center justify-center bg-gradient-to-r from-[#8a6d5b] to-[#a58b79] text-white px-4 py-2 rounded-lg hover:opacity-90 transition cursor-pointer"
              >
                <FiBookOpen className="mr-2" />
                Leitura Atual
              </button>
              <button
                onClick={marcarLido}
                className="flex items-center justify-center bg-gradient-to-r from-[#8a6d5b] to-[#a58b79] text-white px-4 py-2 rounded-lg hover:opacity-90 cursor-pointer"
              >
                <FiBook className="mr-2" />
                Livro Lido
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default LivroBusca;