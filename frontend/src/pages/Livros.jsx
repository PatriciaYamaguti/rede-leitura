import LivroBusca from "../components/LivroBusca";
import Footer from "../components/Footer";
import Header from "../components/Header"

const Livros = () => {
  const idUsuario = sessionStorage.getItem("idUsuario");

  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <div className="flex-grow flex flex-col items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-6">
          <LivroBusca idUsuario={idUsuario} />
      </div>

      <Footer />
    </div>
  );
};

export default Livros;
