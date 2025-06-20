import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { buscarUsuarioPorId } from "../services/api/usuario";
import Header from "../components/Header";
import Footer from "../components/Footer";
import PerfilUsuario from "../components/PerfilUsuario";

const UsuarioDetalhes = () => {
    const { id } = useParams();
    const [usuario, setUsuario] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const buscarDados = async () => {
            try {
                const resultado = await buscarUsuarioPorId(id);

                if (resultado.sucesso) {
                    setUsuario(resultado.usuario);
                }
            } catch (error) {
                console.error("Erro ao carregar dados do usuário:", error);
            } finally {
                setLoading(false);
            }
        };

        buscarDados();
    }, [id]);

    if (loading) return <p>Carregando...</p>;
    if (!usuario) return <p>Usuário não encontrado.</p>;

    return (
        <div className="flex flex-col min-h-screen bg-gray-50">
            <Header />

            <main className="flex-grow py-12 px-4 sm:px-6 lg:px-8">
                <div className="max-w-3xl mx-auto">
                    <PerfilUsuario usuario={usuario} />
                </div>
            </main>

            <Footer />
        </div>
    );
};

export default UsuarioDetalhes;
