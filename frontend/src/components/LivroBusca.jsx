import { useContext, useState } from "react";
import {
  buscarLivrosPorTitulo,
  definirLivroAtual,
  marcarLivroComoLido,
} from "../services/api/livro";
import { AlertContext } from "../contexts/AlertContext";

function LivroBusca({ idUsuario }) {
  const [titulo, setTitulo] = useState("");
  const [sugestoes, setSugestoes] = useState([]);
  const [selecionado, setSelecionado] = useState(null);
  const { showAlert } = useContext(AlertContext);

  const buscarLivros = async () => {
    setSelecionado(null);
    const resposta = await buscarLivrosPorTitulo(titulo);
    if (resposta.sucesso) {
      setSugestoes(resposta.livros);
    } else {
      setSugestoes([]);
      showAlert(resposta.mensagem, "erro");
    }
  };

  const selecionarLivro = (livro) => {
    setSelecionado(livro);
    setSugestoes([]);
    setTitulo(livro.titulo);
  };

  const definirAtual = async () => {
    const resposta = await definirLivroAtual(idUsuario, selecionado.isbn);
    if(resposta.sucesso) {
      showAlert(resposta.mensagem, "sucesso");
    } else {
      showAlert(resposta.mensagem, "erro");
    }
  };

  const marcarLido = async () => {
    const resposta = await marcarLivroComoLido(idUsuario, selecionado.isbn);
    if(resposta.sucesso) {
      showAlert(resposta.mensagem, "sucesso");
    } else {
      showAlert(resposta.mensagem, "erro");
    }
  };

  return (
    <>
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-2xl">
        <h2 className="text-2xl font-bold mb-6 text-center text-[#525050]">
          Adicione suas leituras
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
            className="bg-[#a99484] text-white px-4 rounded-md cursor-pointer transition hover:bg-[#bba799]"
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
      </div>

      {selecionado && (
        <div className="mt-6 pt-4 pb-4 p-8 rounded-xl bg-[#f7f7f7] shadow-md w-full max-w-2xl flex justify-between items-center">
          <p className="text-left text-[#4a4a4a] leading-relaxed">
            <span className="block text-lg font-semibold">{selecionado.titulo}</span>
            <span className="text-sm italic">Autor: {selecionado.autor}</span>
          </p>

          <div className="flex items-center justify-end space-x-3">
            <span className="italic text-sm text-gray-600">Marcar como:</span>
            <button
              onClick={definirAtual}
              className="bg-[#a99484] text-white px-4 py-2 rounded-md hover:bg-[#bba799] transition cursor-pointer"
            >
              Leitura Atual
            </button>
            <button
              onClick={marcarLido}
              className="bg-[#a99484] text-white px-4 py-2 rounded-md hover:bg-[#bba799] transition cursor-pointer"
            >
              Livro Lido
            </button>
          </div>
        </div>
      )}
    </>
  );
}

export default LivroBusca;
