import { useEffect, useState, useContext } from "react";
import { enviarSolicitacaoAmizade, buscarStatusAmizade, recusarSolicitacaoAmizade, aceitarSolicitacaoAmizade } from "../services/api/amizade";
import { AlertContext } from "../contexts/AlertContext";

const PerfilUsuario = ({ usuario }) => {
    const { showAlert } = useContext(AlertContext);
    const idLogado = sessionStorage.getItem("idUsuario");
    const [isAmigo, setIsAmigo] = useState(false);
    const [isSolicitante, setSolicitante] = useState(false);
    const [statusAmizade, setStatusAmizade] = useState(null);
    const [loadingAmizade, setLoadingAmizade] = useState(true);

    useEffect(() => {
        const verificarAmizade = async () => {
            if (idLogado && usuario.id && idLogado !== String(usuario.id)) {
                try {
                    const resultado = await buscarStatusAmizade(idLogado, usuario.id);

                    if (resultado.existeAmizade) {
                        setIsAmigo(true);
                        setStatusAmizade(resultado.status);
                    
                        if(resultado.idSolicitante == idLogado) {
                            setSolicitante(true);
                        }
                    } else {
                        setIsAmigo(false);
                        setStatusAmizade(null);
                    }

                } catch (error) {
                    console.error("Erro ao verificar status de amizade:", error);
                } finally {
                    setLoadingAmizade(false);
                }
            } else {
                setLoadingAmizade(false);
            }
        };

        verificarAmizade();
    }, [idLogado, usuario.id]);

    if (!usuario) return null;

    const handleAdicionarAmigo = async () => {
        try {
            const resultado = await enviarSolicitacaoAmizade(idLogado, usuario.id);
            if (resultado.sucesso) {
                showAlert(resultado.mensagem, "sucesso");
                setIsAmigo(true);
                setStatusAmizade("PENDENTE");
                setSolicitante(idLogado);
            } else {
                showAlert(resultado.mensagem, "erro");
            }
        } catch (error) {
            console.error("Erro ao enviar solicitação de amizade: ", error);
        }
    };

    const handleDesfazerAmizade = async () => {
        try {
            const resultadoStatus = await buscarStatusAmizade(idLogado, usuario.id);

            if (resultadoStatus.existeAmizade && resultadoStatus.idAmizade) {
                const idAmizadeAtual = resultadoStatus.idAmizade;

                const resultado = await recusarSolicitacaoAmizade(idAmizadeAtual);

                if (resultado.sucesso) {
                    showAlert(resultado.mensagem, "sucesso");
                    setIsAmigo(false);
                    setStatusAmizade(null);
                } else {
                    showAlert(resultado.mensagem, "erro");
                }
            } else {
                showAlert("ID da amizade não encontrado.", "erro");
            }
        } catch (error) {
            console.error("Erro ao desfazer amizade:", error);
        }
    };

    const handleAceitarAmizade = async () => {
        try {
            const resultadoStatus = await buscarStatusAmizade(idLogado, usuario.id);

            if (resultadoStatus.existeAmizade && resultadoStatus.idAmizade) {
                const idAmizadeAtual = resultadoStatus.idAmizade;

                const resultado = await aceitarSolicitacaoAmizade(idAmizadeAtual);

                if (resultado.sucesso) {
                    showAlert(resultado.mensagem, "sucesso");
                    setIsAmigo(true);
                    setStatusAmizade("ACEITA");
                } else {
                    showAlert(resultado.mensagem, "erro");
                }
            } else {
                showAlert("ID da amizade não encontrado.", "erro");
            }
        } catch (error) {
            console.error("Erro ao desfazer amizade:", error);
        }
    };

    const livrosLidos = usuario.livrosLidos ?? [];

    return (
        <div className="bg-white rounded-xl shadow-lg overflow-hidden">
            <div className="bg-gradient-to-r from-[#8a6d5b] to-[#a58b79] p-6 text-white flex justify-between items-center">
                <div>
                    <h2 className="text-3xl font-bold">{usuario.nome}</h2>
                    <p className="text-blue-100">@{usuario.usuario}</p>
                </div>

                {!loadingAmizade && idLogado !== String(usuario.id) && (
                    <div className="flex space-x-2">
                        {isAmigo && statusAmizade === "PENDENTE" ? (
                            isSolicitante ? (
                                <button
                                    onClick={handleDesfazerAmizade}
                                    className="px-4 py-2 text-sm font-medium rounded-lg bg-white/10 hover:bg-white/20 backdrop-blur-sm border border-white/20 transition-all"
                                >
                                    Cancelar solicitação
                                </button>
                            ) : (
                                <div className="flex gap-2">
                                    <button
                                        onClick={handleDesfazerAmizade}
                                        className="px-4 py-2 text-sm font-medium rounded-lg bg-white/10 hover:bg-white/20 backdrop-blur-sm border border-white/20 transition-all"
                                    >
                                        Recusar solicitação
                                    </button>
                                        <button
                                        onClick={handleAceitarAmizade}
                                        className="px-4 py-2 text-sm font-medium rounded-lg bg-white text-[#8a6d5b] hover:bg-white/90 transition-all flex items-center space-x-1"
                                    >
                                        Aceitar solicitação
                                    </button>
                                </div>
                            )
                            
                        ) : isAmigo && statusAmizade === "ACEITA" ? (
                        <button
                            onClick={handleDesfazerAmizade}
                            className="px-4 py-2 text-sm font-medium rounded-lg bg-white/10 hover:bg-white/20 backdrop-blur-sm border border-white/20 transition-all"
                        >
                            Remover amigo
                        </button>
                        ) : (
                        <button
                            onClick={handleAdicionarAmigo}
                            className="px-4 py-2 text-sm font-medium rounded-lg bg-white text-[#8a6d5b] hover:bg-white/90 transition-all flex items-center space-x-1"
                        >
                            <span>+</span>
                            <span>Adicionar amigo</span>
                        </button>
                        )}
                    </div>
                    
                )}
            </div>

            <div className="p-6 space-y-6">
                {usuario.descricao && (
                    <div className="bg-gray-50 rounded-lg p-4">
                        <h3 className="text-sm font-semibold text-gray-500 mb-1">SOBRE</h3>
                        <p className="text-gray-700">{usuario.descricao}</p>
                    </div>
                )}

                {usuario.livroAtual && (
                    <div className="bg-[#dddad750] rounded-lg p-4 border border-[#d3cec8]">
                        <h3 className="text-lg font-semibold mb-2">Lendo atualmente</h3>
                        <p className="font-medium">{usuario.livroAtual.titulo}</p>
                        <p className="text-gray-600">por {usuario.livroAtual.autor}</p>
                    </div>
                )}

                <div>
                    <h3 className="text-xl font-semibold text-gray-800 mb-4 pb-2 border-b border-gray-200">
                        Livros lidos
                    </h3>

                    {livrosLidos.length > 0 ? (
                        <ul className="space-y-4">
                            {livrosLidos.map((livro, index) => (
                                <li key={index} className="flex items-start">
                                    <div className="bg-gray-100 rounded-full w-10 h-10 p-2 mr-3 flex justify-center items-center">
                                        <span className="text-gray-500">#{index + 1}</span>
                                    </div>
                                    <div className="flex-1">
                                        <p className="font-medium text-gray-900">{livro.titulo}</p>
                                        <p className="text-gray-600 text-sm">por {livro.autor}</p>
                                        <p className="text-gray-500 text-xs mt-1">
                                            <span className="font-medium">Concluído em:</span> {new Date(livro.dataLeitura).toLocaleDateString('pt-BR', { day: '2-digit', month: 'long', year: 'numeric' })}
                                        </p>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <div className="text-center py-8">
                            <p className="text-gray-500">Este usuário ainda não marcou nenhum livro como lido.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default PerfilUsuario;