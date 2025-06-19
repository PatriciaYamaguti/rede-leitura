import LivroBusca from "../components/LivroBusca";
import Footer from "../components/Footer";

const Livros = () => {
  const idUsuario = 1; // como vamos pegar o id do usuário logado?

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      <div className="flex-grow flex items-center justify-center p-4">
        <LivroBusca idUsuario={idUsuario} />
      </div>
      <Footer />
    </div>
  );
};

export default Livros;
