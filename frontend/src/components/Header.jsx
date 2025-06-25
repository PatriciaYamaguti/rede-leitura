import { Link } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import { FiUser, FiBook, FiLogOut, FiEdit2, FiUsers, FiUserPlus, FiInbox } from "react-icons/fi";
import { listarAmizadeLog, marcarComoLido } from "../services/api/amizade";

const Header = () => {
    const [menuAberto, setMenuAberto] = useState(false);
    const [caixaAberta, setCaixaAberta] = useState(false);
    const [logsAmizade, setLogsAmizade] = useState([]);
    const [quantidadeNotificacoes, setQuantidadeNotificacoes] = useState(0);
    const menuRef = useRef(null);
    const caixaRef = useRef(null);
    const idLogado = sessionStorage.getItem("idUsuario");

    const toggleMenu = () => setMenuAberto((prev) => !prev);
    const toggleCaixa = () => setCaixaAberta((prev) => !prev);

    useEffect(() => {
        const handleClickFora = (event) => {
            if (menuRef.current && !menuRef.current.contains(event.target)) {
                setMenuAberto(false);
            }
            if (caixaRef.current && !caixaRef.current.contains(event.target)) {
                setCaixaAberta(false);
            }
        };

        document.addEventListener("mousedown", handleClickFora);
        return () => document.removeEventListener("mousedown", handleClickFora);
    }, []);

    useEffect(() => {
        const carregarLogsAmizade = async () => {
            if (idLogado) {
                const resultado = await listarAmizadeLog(idLogado);
                if (resultado.sucesso) {
                    setLogsAmizade(resultado.logs);
                    const pendentes = resultado.logs.filter(log => log.status === "PENDENTE").length;
                    setQuantidadeNotificacoes(pendentes);
                }
            }
        };

        carregarLogsAmizade();
    }, [caixaAberta, idLogado]);


    return (
        <header className="w-full py-8 bg-gradient-to-r from-[#8a6d5b] to-[#a58b79] shadow-lg">
            <nav className="max-w-7xl mx-auto flex justify-between items-center px-6 py-4">
                <div className="flex items-center space-x-2">
                    <Link to="/" className="text-2xl font-bold text-white mr-8">
                        RedeLeitura
                    </Link>

                    <div className="hidden md:flex items-center gap-6">
                        <Link
                            to="/descobrir"
                            className="flex items-center text-white text-base font-medium hover:text-gray-100 transition px-3 py-2 rounded-lg hover:bg-white/10"
                        >
                            <FiUserPlus className="mr-2" />
                            Encontrar amigos
                        </Link>
                        <Link
                            to="/amigos"
                            className="flex items-center text-white text-base font-medium hover:text-gray-100 transition px-3 py-2 rounded-lg hover:bg-white/10"
                        >
                            <FiUsers className="mr-2" />
                            Seus amigos
                        </Link>
                    </div>
                </div>

                <div className="flex items-center space-x-4">
                    <div className="relative" ref={caixaRef}>
                        <button
                            onClick={toggleCaixa}
                            className={`w-10 h-10 rounded-full bg-white/20 text-white flex items-center justify-center hover:bg-white/30 transition cursor-pointer border border-white/30 ${quantidadeNotificacoes > 0 ? 'pulse-notification' : 'bg-white/20 hover:bg-white/30 text-white'}`}
                            aria-label="Caixa de Entrada"
                        >
                            <FiInbox size={20} />
                        </button>

                        {caixaAberta && (
                            <div
                                className="absolute right-0 mt-2 w-72 max-h-80 overflow-y-auto bg-white rounded-lg shadow-xl z-50 border border-gray-100 animate-slide-fade"
                            >
                                <div className="px-4 py-3 border-b border-gray-100">
                                    <p className="text-sm font-medium text-gray-900">Caixa de Entrada</p>
                                </div>

                                {logsAmizade.length > 0 ? (
                                    logsAmizade.map((log, index) => (
                                        <div
                                            key={index}
                                            className="block px-4 py-3 text-sm text-gray-700 border-b border-gray-100 hover:bg-gray-50 transition"
                                        >
                                            <Link
                                                to={`/usuario/${log.idUsuario}`}
                                                onClick={() => setCaixaAberta(false)}
                                                className="font-medium text-gray-900 block"
                                            >
                                                {log.nomeUsuario}
                                            </Link>

                                            <div className="text-gray-600 text-sm mb-1">
                                                {log.descricao} — {new Date(log.dataHora).toLocaleDateString('pt-BR', {
                                                    day: '2-digit', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit'
                                                })}
                                            </div>

                                            <button
                                                onClick={async () => {
                                                    await marcarComoLido(idLogado, log.idUsuario);

                                                    const resultadoAtualizado = await listarAmizadeLog(idLogado);
                                                    if (resultadoAtualizado.sucesso) {
                                                        setLogsAmizade(resultadoAtualizado.logs);
                                                        const pendentes = resultadoAtualizado.logs.filter(l => l.status === "PENDENTE").length;
                                                        setQuantidadeNotificacoes(pendentes);
                                                    }
                                                }}
                                                className="text-gray-600 text-xs underline hover:text-gray-800 cursor-pointer"
                                            >
                                                Marcar como lido
                                            </button>
                                        </div>
                                    ))
                                ) : (
                                    <div className="px-4 py-3 text-sm text-gray-500">Nenhuma notificação de amizade.</div>
                                )}
                            </div>
                        )}
                    </div>

                    <div className="relative" ref={menuRef}>
                        <button
                            onClick={toggleMenu}
                            className="w-10 h-10 rounded-full bg-white/20 text-white flex items-center justify-center hover:bg-white/30 transition cursor-pointer border border-white/30"
                            aria-label="Menu do usuário"
                        >
                            <FiUser size={20} />
                        </button>

                        {menuAberto && (
                            <div
                                className="absolute right-0 mt-2 w-56 bg-white rounded-lg shadow-xl overflow-hidden z-50
                                    transition ease-out duration-200 transform opacity-100 translate-y-0
                                    animate-slide-fade border border-gray-100"
                            >
                                <div className="px-4 py-3 border-b border-gray-100">
                                    <p className="text-sm font-medium text-gray-900">Menu do Usuário</p>
                                </div>
                                <Link
                                    to={`/usuario/${idLogado}`}
                                    onClick={() => setMenuAberto(false)}
                                    className="flex items-center px-4 py-3 text-sm text-gray-700 hover:bg-gray-50 transition"
                                >
                                    <FiUser className="mr-3 text-gray-500" />
                                    Seu perfil
                                </Link>
                                <Link
                                    to="/perfil"
                                    onClick={() => setMenuAberto(false)}
                                    className="flex items-center px-4 py-3 text-sm text-gray-700 hover:bg-gray-50 transition"
                                >
                                    <FiEdit2 className="mr-3 text-gray-500" />
                                    Editar perfil
                                </Link>
                                <Link
                                    to="/livros"
                                    onClick={() => setMenuAberto(false)}
                                    className="flex items-center px-4 py-3 text-sm text-gray-700 hover:bg-gray-50 transition"
                                >
                                    <FiBook className="mr-3 text-gray-500" />
                                    Gerenciar livros
                                </Link>
                                <div className="border-t border-gray-100">
                                    <Link
                                        to="/logout"
                                        onClick={() => setMenuAberto(false)}
                                        className="flex items-center px-4 py-3 text-sm text-gray-700 hover:bg-gray-50 transition"
                                    >
                                        <FiLogOut className="mr-3" />
                                        Sair
                                    </Link>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </nav>
        </header>
    );
};

export default Header;