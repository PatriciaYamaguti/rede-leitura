import CadastroAttForm from "../components/CadastroAttForm";
import Footer from "../components/Footer";

const Cadastro = () => {
    return (
        <div className="flex flex-col min-h-screen">
            
            <div className="flex-grow flex flex-col items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-6">
                <CadastroAttForm tipo="Cadastro" />
            </div>

            <Footer />
        </div>
    );
};

export default Cadastro;
