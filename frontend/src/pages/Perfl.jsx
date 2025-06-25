import CadastroAttForm from "../components/CadastroAttForm";
import Footer from "../components/Footer";
import Header from "../components/Header";

const Perfil = () => {
    return (
    <div className="flex flex-col min-h-screen">
        <Header />
        <div className="flex-grow flex flex-col items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-10 py-20">
            <CadastroAttForm tipo="Atualização" />
        </div>
        <Footer />
    </div>
    )
}

export default Perfil;