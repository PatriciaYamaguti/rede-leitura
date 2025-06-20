import { useEffect, useState } from "react";
import CardUsuario from "../components/CardUsuario";
import Footer from "../components/Footer";
import Header from "../components/Header";
import { listarUsuariosPorInteresses } from "../services/api/usuario";

const Descobrir = () => {
    const [usuarios, setUsuarios] = useState([]);

    const buscarUsuarios = async () => {
        const idUsuario = sessionStorage.getItem("idUsuario");
        
        if(idUsuario) {
            const retorno = await listarUsuariosPorInteresses(idUsuario);

            console.log(retorno);

            if (retorno.sucesso) {
                setUsuarios(retorno.usuarios)
            }
        }
    }

    useEffect(() => {
        buscarUsuarios();
    }, [])

    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <div className="flex-grow flex flex-col items-center justify-center bg-gradient-to-br bg-[#f0f0f0] p-10 py-20">
                <div className="w-full max-w-4xl grid gap-6">
                    { usuarios.length > 0 ? (
                        usuarios.map((usuario, index) => (
                            <CardUsuario key={index} usuario={usuario} />
                        ))
                    ) : (
                        <p className="text-gray-600">Nenhum usuário encontrado.</p>
                    )}
                </div>
            </div>
            <Footer />
        </div>
    )
}

export default Descobrir;