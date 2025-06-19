import { useState } from "react";
import {
  buscarLivrosPorTitulo,
  definirLivroAtual,
  marcarLivroComoLido,
} from "../services/api/livro";

function LivroBusca({ idUsuario }) {
  const [titulo, setTitulo] = useState("");
  const [sugestoes, setSugestoes] = useState([]);
  const [selecionado, setSelecionado] = useState(null);

  const buscarLivros = async () => {
    setSelecionado(null);
    const resposta = await buscarLivrosPorTitulo(titulo);
    if (resposta.sucesso) {
      setSugestoes(resposta.livros);
    } else {
      setSugestoes([]);
      alert(resposta.mensagem)
    }
  };

  const selecionarLivro = (livro) => {
    setSelecionado(livro);
    setSugestoes([]);
    setTitulo(livro.titulo);
  };

  const definirAtual = async () => {
    const resposta = await definirLivroAtual(idUsuario, selecionado.isbn);
    alert(resposta.mensagem);
  };

  const marcarLido = async () => {
    const resposta = await marcarLivroComoLido(idUsuario, selecionado.isbn);
    alert(resposta.mensagem);
  };

  return (
    <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-2xl font-bold mb-6 text-center text-[#525050]">
          Adicionar leituras
        </h2>

        <div className="flex space-x-2 mb-2">
          <input
            type="text"
            value={titulo}
            placeholder="Digite o título"
            onChange={(e) => {
              setTitulo(e.target.value);
              setSelecionado(null);
            }}
            className="flex-grow border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:ring focus:ring-[#a99484]"
          />
          <button
            onClick={buscarLivros}
            className="bg-[#a99484] text-white px-4 rounded-md cursor-pointer hover:bg-[#8a7c72]"
          >
            Buscar
          </button>
        </div>

        {sugestoes.length > 0 && !selecionado && (
          <ul className="border border-gray-200 mt-1 rounded shadow-sm">
            {sugestoes.map((livro) => (
              <li
                key={livro.isbn}
                onClick={() => selecionarLivro(livro)}
                className="px-3 py-2 cursor-pointer hover:bg-gray-100 text-sm"
              >
                <strong>{livro.titulo}</strong> — {livro.autor}
              </li>
            ))}
          </ul>
        )}

        {selecionado && (
          <div className="mt-6 border p-4 rounded bg-gray-50 text-center">
            <p className="mb-3">
              <strong>{selecionado.titulo}</strong> — {selecionado.autor}
            </p>
            <div className="flex justify-center space-x-3">
              <button
                onClick={definirAtual}
                className="bg-yellow-500 text-white px-4 py-1 rounded-md hover:bg-yellow-600"
              >
                Definir como atual
              </button>
              <button
                onClick={marcarLido}
                className="bg-green-600 text-white px-4 py-1 rounded-md hover:bg-green-700"
              >
                Marcar como lido
              </button>
            </div>
          </div>
        )}
      </div>
  );
}

export default LivroBusca;
