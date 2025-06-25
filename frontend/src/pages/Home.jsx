import Header from "../components/Header";
import Footer from "../components/Footer";
import DeveloperCard from "../components/DeveloperCard";

const Home = () => {
  const developers = [
    {
      name: "Vitor do Nascimento",
      description:
        "Comecei na programação apenas como um hobbie desenvolvendo jogos. Hoje estudo e busco me especializar na area.",
      image: "https://avatars.githubusercontent.com/u/181667475?v=4",
      github: "https://github.com/vitornsc7",
    },
    {
      name: "Patricia Yamaguti",
      description:
        "",
      image: "https://avatars.githubusercontent.com/u/180965990?v=4",
      github: "https://github.com/PatriciaYamaguti",
    },
  ];

  return (
    <div className="flex flex-col min-h-screen bg-gradient-to-br bg-[#f0f0f0]">
      <Header />

      <main className="flex-grow flex flex-col items-center px-4 py-12 md:py-20">
        <section className="max-w-4xl w-full text-center mb-16 animate-fade-in">
          <div className="mb-10">
            <h2 className="text-5xl md:text-6xl font-bold text-[#4e342e] mb-6 tracking-tight leading-tight">
              Bem-vindo ao <span className="text-[#8d6e63]">Rede Leitura</span>
            </h2>
          </div>

          <p className="text-lg text-justify md:text-xl text-gray-800 leading-relaxed">
            Desenvolvido no 3º semestre de Análise e Desenvolvimento de Sistemas da Faculdade Senac, o Rede Leitura cria pontes entre amantes de literatura para compartilhar experiências e descobrir novas narrativas.
          </p>
        </section>

        <section className="w-full max-w-5xl">
          <div className="mb-12 text-center">
            <h2 className="text-3xl font-bold text-[#8d6e63] mb-2">
              Desenvovido por
            </h2>
            <div className="w-24 h-1 bg-[#8d6e63] mx-auto rounded-full"></div>
          </div>

          <div className="grid grid-cols-1 gap-4">
            {developers.map((dev, index) => (
              <DeveloperCard
                key={index}
                name={dev.name}
                description={dev.description}
                image={dev.image}
                github={dev.github}
              />
            ))}
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
};

export default Home;
