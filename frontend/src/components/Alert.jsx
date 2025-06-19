import { useEffect, useState } from "react";

const Alert = ({ tipo, mensagem, onClose }) => {
  const [visivel, setVisivel] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisivel(false);
      setTimeout(onClose, 500);
    }, 3000);

    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <div
      className={`
        fixed top-0 left-0 w-full text-center py-3 font-semibold text-white z-50 transition-all duration-500
        ${visivel ? "opacity-100 translate-y-0" : "opacity-0 -translate-y-5"}
        ${tipo === "sucesso" ? "bg-green-500" : "bg-[#AA382A]"}
      `}
    >
      {mensagem}
    </div>
  );
};

export default Alert;
