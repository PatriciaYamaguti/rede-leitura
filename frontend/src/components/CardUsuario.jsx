import { FiUser } from "react-icons/fi";
import { Link } from "react-router-dom";

const CardUsuario = ({ usuario }) => {
    return (
        <Link to={`/usuario/${usuario.id}`} className="w-72 h-60 p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 bg-white cursor-pointer">
            <div className="flex items-center gap-4 mb-4">
                <div className="p-3 rounded-full bg-gray-100">
                    <FiUser size={20} className="text-gray-600" />
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800">{usuario.nome}</h3>
                    <p className="text-sm text-gray-500">@{usuario.usuario}</p>
                </div>
            </div>

            <div className="space-y-4">
                {usuario.descricao && (
                    <p className="text-gray-600 text-sm line-clamp-3">
                        {usuario.descricao}
                    </p>
                )}

                <div className="text-sm mt-4">
                    {usuario.quantidadeLivrosEmComum > 0 ? (
                        <p className="text-blue-600 font-medium">
                            Vocês possuem {usuario.quantidadeLivrosEmComum} {usuario.quantidadeLivrosEmComum === 1 ? "leitura" : "leituras"} em comum
                        </p>
                    ) : (
                        <p className="text-gray-500">
                            Vocês não possuem leituras em comum
                        </p>
                    )}
                </div>
            </div>
        </Link>
    );
};

export default CardUsuario;