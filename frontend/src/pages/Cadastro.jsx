import CadastroForm from "../components/CadastroForm";
import Footer from "../components/Footer";

const Cadastro = () => {
    return (
        <div className="flex flex-col min-h-screen">
            {/* Conteúdo principal */}
            <div className="flex-grow flex items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-6">
                <CadastroForm />
            </div>

            {/* Footer sempre no rodapé */}
            <Footer />
        </div>
    );
};

export default Cadastro;
