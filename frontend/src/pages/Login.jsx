import LoginForm from "../components/LoginForm";

const Cadastrar = () => {
    return (
        <div className="flex items-center justify-center min-h-screen bg-gradient-to-br bg-[#f0f0f0] p-6">
            <div className="max-w-4xl w-full overflow-hidden flex flex-col md:flex-row p-5">
                
                {/* Seção de Texto */}
                <div className="md:w-1/2 p-8 flex flex-col justify-center">
                    <h1 className="text-5xl font-bold text-[#927362] mb-4">Rede Leitura</h1>
                    <p className="text-gray-700 text-xl leading-relaxed">
                        Crie seu portfólio de leitura e conheça novos amigos com gostos literários parecidos com os seus!
                    </p>
                </div>

                <LoginForm />
            </div>
        </div>
    );
};

export default Cadastrar;
