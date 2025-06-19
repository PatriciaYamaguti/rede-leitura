import { Link } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import { FiUser } from "react-icons/fi";

const Header = () => {
    const [menuAberto, setMenuAberto] = useState(false);
    const menuRef = useRef(null);

    const toggleMenu = () => setMenuAberto((prev) => !prev);

    // Fechar ao clicar fora
    useEffect(() => {
        const handleClickFora = (event) => {
            if (menuRef.current && !menuRef.current.contains(event.target)) {
                setMenuAberto(false);
            }
        };

        if (menuAberto) {
            document.addEventListener("mousedown", handleClickFora);
        } else {
            document.removeEventListener("mousedown", handleClickFora);
        }

        return () => document.removeEventListener("mousedown", handleClickFora);
    }, [menuAberto]);

    return (
        <header className="w-full shadow-md bg-[#978074]">
            <nav className="max-w-5xl mx-auto flex justify-between items-center px-6 py-4">
                {/* Links de navegação */}
                <div className="flex gap-8">
                    <Link
                        to="/descobrir"
                        className="text-white text-base font-medium hover:underline hover:text-gray-100 transition"
                    >
                        Encontrar amigos
                    </Link>
                    <Link
                        to="/amigos"
                        className="text-white text-base font-medium hover:underline hover:text-gray-100 transition"
                    >
                        Seus amigos
                    </Link>
                </div>

                {/* Área de perfil */}
                <div className="relative" ref={menuRef}>
                    <button
                        onClick={toggleMenu}
                        className="w-10 h-10 rounded-full bg-white text-[#978074] flex items-center justify-center hover:bg-gray-100 transition cursor-pointer"
                        aria-label="Menu do usuário"
                    >
                        <FiUser size={22} />
                    </button>

                    {menuAberto && (
                        <div className="absolute right-0 mt-3 w-48 bg-white rounded-lg shadow-lg overflow-hidden z-50 animate-fade-in">
                            <Link
                                to="/perfil"
                                onClick={() => setMenuAberto(false)}
                                className="block px-4 py-3 text-sm text-gray-700 hover:bg-gray-100 transition"
                            >
                                Editar perfil
                            </Link>
                            <Link
                                to="/livros"
                                onClick={() => setMenuAberto(false)}
                                className="block px-4 py-3 text-sm text-gray-700 hover:bg-gray-100 transition"
                            >
                                Gerenciar livros
                            </Link>
                            <Link
                                to="/logout"
                                onClick={() => setMenuAberto(false)}
                                className="block px-4 py-3 text-sm underline hover:bg-gray-100 transition"
                            >
                                Sair
                            </Link>
                        </div>
                    )}
                </div>
            </nav>
        </header>
    );
};

export default Header;