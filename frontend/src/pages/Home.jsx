import Header from "../components/Header";
import Footer from "../components/Footer";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="flex flex-col min-h-screen bg-[#fafafa]">
      <Header />

      <main className="flex-grow flex flex-col items-center justify-center px-6 py-20 bg-gradient-to-br from-[#f7ede2] to-[#fff6e5]">
        <h1 className="text-4xl md:text-5xl font-bold text-[#5c4033] mb-6 text-center">
          Bem-vindo ao RedeLeitura 📚✨
        </h1>
      </main>
      <Footer />
    </div>
  );
};

export default Home;
