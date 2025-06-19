import { Route, Routes } from "react-router-dom";
import "./App.css";
import Cadastro from "./pages/Cadastro";
import Login from "./pages/Login";
import Livros from "./pages/Livros";
import { AlertProvider } from "./providers/AlertProvider";
import PrivateRoute from "./components/PrivateRoute";

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
            <PrivateRoute>
              <Livros />
            </PrivateRoute>
          }
        />
        <Route
          path="/descobrir"
          element={
            <PrivateRoute>
              
            </PrivateRoute>
          }
        />
        <Route
          path="/amigos"
          element={
            <PrivateRoute>
              
            </PrivateRoute>
          }
        />
      </Routes>
    </AlertProvider>
  );
}

export default App;