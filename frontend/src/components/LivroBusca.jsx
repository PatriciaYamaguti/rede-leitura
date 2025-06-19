import { useState } from "react";
import { buscarLivrosPorTitulo } from "../services/api/livro";

function LivroBusca({ idUsuario }) {
  const [titulo, setTitulo] = useState("");

  const buscarLivros = async () => {
    const resposta = await buscarLivrosPorTitulo(titulo);
    console.log(resposta);
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 px-4">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-2xl font-semibold mb-6 text-center text-[#3b3b3b]">
          Buscar Livros
        </h2>

        <div className="flex space-x-2 mb-2">
          <input
            type="text"
            value={titulo}
            placeholder="Digite o título"
            onChange={(e) => setTitulo(e.target.value)}
            className="flex-grow border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:ring focus:ring-[#a99484]"
          />
          <button
            onClick={buscarLivros}
            className="bg-[#a99484] text-white px-4 rounded-md hover:bg-[#8a7c72]"
          >
            Buscar
          </button>
        </div>
      </div>
    </div>
  );
}

export default LivroBusca;
