import { Route, Routes } from "react-router-dom";
import "./App.css";
import Cadastro from "./pages/Cadastro";
import Login from "./pages/Login";
import Livros from "./pages/Livros";
import { AlertProvider } from "./providers/AlertProvider";
import ValidacaoRoute from "./components/ValidacaoRoute";
import Perfil from "./pages/Perfl";
import Descobrir from "./pages/Descobrir";
import UsuarioDetalhes from "./pages/UsuarioDetalhes";

function App() {
  return (
    <AlertProvider>
      <Routes>
        <Route path="/" element={<></>} />
        <Route path="/cadastrar" element={<Cadastro />} />
        <Route path="/logar" element={<Login />} />

        <Route
          path="/livros"
          element={
            <ValidacaoRoute>
              <Livros />
            </ValidacaoRoute>
          }
        />
        <Route
          path="/perfil"
          element={
            <ValidacaoRoute>
              <Perfil />
            </ValidacaoRoute>
          }
        />

        <Route
          path="/descobrir"
          element={
            <ValidacaoRoute>
              <Descobrir />
            </ValidacaoRoute>
          }
        />

        <Route
          path="/usuario/:id"
          element={
            <ValidacaoRoute>
              <UsuarioDetalhes />
            </ValidacaoRoute>
          }
        />
      </Routes>
    </AlertProvider>
  );
}

export default App;
