import LivroBusca from "../components/LivroBusca";
import Footer from "../components/Footer";

const Livros = () => {
  const idUsuario = 1; // como vamos pegar o id do usuário logado?

  return (
    <div className="flex flex-col min-h-screen">
            {/* Conteúdo principal */}
            <div className="flex-grow flex flex-col items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-6">
                <LivroBusca idUsuario={idUsuario} />
            </div>

            <Footer />
        </div>
  );
};

export default Livros;
