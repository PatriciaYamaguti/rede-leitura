import { useEffect, useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import CardUsuario from "../components/CardUsuario";
import { listarAmigos } from "../services/api/amizade";

const Amigos = () => {
  const [amigos, setAmigos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const buscarAmigos = async () => {
    const idUsuario = sessionStorage.getItem("idUsuario");

    if (!idUsuario) return;

    const retorno = await listarAmigos(idUsuario);

    if (retorno.sucesso) {
      setAmigos(retorno.amigos);
    }

    setIsLoading(false);
  };

  useEffect(() => {
    buscarAmigos();
  }, []);

  return (
    <div className="flex flex-col min-h-screen bg-[#f0f0f0]">
      <Header />

      <main className="flex-grow p-10 py-20 bg-gradient-to-br">
        <div className="mx-auto max-w-4xl">
          <h2 className="text-2xl font-medium mb-10 text-left text-gray-800">
            Amigos
          </h2>

          {isLoading ? (
            <p className="text-center text-gray-600">Carregando amigos...</p>
          ) : amigos.length > 0 ? (
            <div className="grid grid-cols-3 gap-6">
              {amigos.map((amigo) => (
                <CardUsuario key={amigo.id} usuario={amigo} />
              ))}
            </div>
          ) : (
            <p className="text-center text-gray-600">
              Você ainda não possui amigos.
            </p>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default Amigos;
