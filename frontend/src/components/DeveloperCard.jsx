const DeveloperCard = ({ name, description, image, github }) => {
    return (
        <a
            href={github}
            target="_blank"
            rel="noopener noreferrer"
            className="block transform transition-transform"
        >
            <div className="group bg-gradient-to-br from-white to-[#f9f9f9] rounded-3xl p-6 shadow-md hover:shadow-lg transition-all duration-300 flex items-center gap-6 h-full">
                <div className="relative flex-shrink-0">
                    <img
                        src={image}
                        alt={name}
                        className="w-28 h-28 min-w-[7rem] rounded-full object-cover shadow-md transition-all"
                    />
                </div>

                <div className="flex-1">
                    <h3 className="text-2xl font-bold text-[#4e342e] mb-2 group-hover:text-[#5d4037] transition-colors">
                        {name}
                    </h3>
                    <p className="text-gray-800 text-justify leading-relaxed">
                        {description}
                    </p>
                    <div className="mt-3 flex items-center text-[#8d6e63] text-sm">
                        <span>Ver perfil no GitHub</span>
                    </div>
                </div>
            </div>
        </a>
    );
};

export default DeveloperCard;