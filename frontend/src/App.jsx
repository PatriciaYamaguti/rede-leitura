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
import Amigos from "./pages/Amigos";
import Logout from "./pages/Logout";
import Home from "./pages/Home";

function App() {
  return (
    <AlertProvider>
      <Routes>
        <Route path="/cadastrar" element={<Cadastro />} />
        <Route path="/logar" element={<Login />} />

        <Route
          path="/"
          element={
            <ValidacaoRoute>
              <Home />
            </ValidacaoRoute>
          }
        />

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
          path="/amigos"
          element={
            <ValidacaoRoute>
              <Amigos />
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
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </AlertProvider>
  );
}

export default App;
