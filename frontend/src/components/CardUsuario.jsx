import { FiUser } from "react-icons/fi";

const CardUsuario = ( { usuario } ) => {
    return (
        <div className="h-70 w-fit p-5 shadow-md">
            <div className="flex">
                <FiUser size={20} />
                <div>
                    <p>{usuario.nome}</p>
                    <p>{usuario.usuario}</p>
                </div>
            </div>
            <div className="flex flex-col justify-between h-full">
                <p>{usuario.descricao}</p>

                {usuario.quantidadeLivrosEmComum > 0 ? (
                    <p>Vocês possuem { usuario.quantidadeLivrosEmComum } leituras em comum</p>
                ) : (
                    <p>Vocês não possuem nenhuma leitura em comum</p>
                )}
            </div>
        </div>
    )
}

export default CardUsuario;