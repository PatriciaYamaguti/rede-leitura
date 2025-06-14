const Footer = () => {
    return (
        <footer className="bg-white text-gray-600 py-12 mt-auto">
            <div className="max-w-4xl mx-auto px-4 text-sm">
                <div className="flex flex-col md:flex-row items-center justify-between gap-2">
                    <p>&copy; {new Date().getFullYear()} Rede Leitura</p>
                    <div className="text-center md:text-right">
                        <span>Desenvolvido por </span>
                        <span className="font-semibold">Patricia Costa</span>
                        <span> e </span>
                        <span className="font-semibold">Vitor do Nascimento</span>
                    </div>
                </div>
                <p className="mt-6 text-center md:text-left">
                    Trabalho do terceiro semestre da faculdade de Análise e Desenvolvimento de Sistemas - SENAC.
                </p>
            </div>
        </footer>
    );
};

export default Footer;
