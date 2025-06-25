import { useEffect, useState } from "react";
import CardUsuario from "../components/CardUsuario";
import Footer from "../components/Footer";
import Header from "../components/Header";
import LivroBusca from "../components/LivroBusca";
import { listarUsuariosPorInteresses } from "../services/api/usuario";
import { listarAmigos } from "../services/api/amizade";

const Descobrir = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const buscarUsuarios = async () => {
    try {
      const idUsuario = sessionStorage.getItem("idUsuario");

      if (!idUsuario) return;

      const [resUsuarios, resAmigos] = await Promise.all([
        listarUsuariosPorInteresses(idUsuario),
        listarAmigos(idUsuario),
      ]);

      if (resUsuarios.sucesso && resAmigos.sucesso) {
        const idsAmigos = resAmigos.amigos.map((a) => a.id);

        const usuariosFiltrados = resUsuarios.usuarios.filter(
          (u) => !idsAmigos.includes(u.id)
        );

        setUsuarios(usuariosFiltrados);
      }
    } catch (error) {
      console.error("Erro ao buscar usuários:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    buscarUsuarios();
  }, []);

  return (
    <div className="flex flex-col min-h-screen bg-[#f0f0f0]">
      <Header />

      <main className="flex-grow p-10 py-20 bg-gradient-to-br">
        <div className="mx-auto max-w-4xl">
          <h2 className="text-2xl font-medium mb-10 text-left text-gray-800">
            Encontre novos amigos!
          </h2>

          {isLoading ? (
            <div className="flex justify-center">
              <p className="text-gray-600">Carregando...</p>
            </div>
          ) : usuarios.length > 0 ? (
            <div className="grid grid-cols-3 gap-6">
              {usuarios.map((usuario) => (
                <CardUsuario key={usuario.id} usuario={usuario} />
              ))}
            </div>
          ) : (
            <p className="text-center text-gray-600">
              Nenhum usuário novo encontrado.
            </p>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default Descobrir;
