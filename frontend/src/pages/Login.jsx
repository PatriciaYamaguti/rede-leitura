import Footer from "../components/Footer";
import LoginForm from "../components/LoginForm";

const Login = () => {
    return (
        <div className="flex flex-col min-h-screen">
            <div className="flex-grow flex items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-6">
                <div className="max-w-4xl py-10 w-full overflow-hidden flex flex-col md:flex-row">
                    <div className="md:w-1/2 p-8 flex flex-col justify-center">
                        <h1 className="text-5xl font-bold text-[#927362] mb-4">Rede Leitura</h1>
                        <p className="text-gray-700 text-xl leading-relaxed">
                            Crie seu portfólio de leitura e conheça novos amigos com gostos literários parecidos com os seus!
                        </p>
                    </div>
                    <LoginForm />
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Login;
