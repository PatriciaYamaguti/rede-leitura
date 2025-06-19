import { Route, Routes } from 'react-router-dom'
import './App.css'
import Cadastro from './pages/Cadastro'
import Login from './pages/Login'
import Livros from './pages/Livros'

function App() {
  
  return (
    <div>
      <Routes>
        <Route path="/" element={<></>} />
        <Route path="/cadastrar" element={<Cadastro />} />
        <Route path="/logar" element={<Login />} />
        <Route path="/livros" element={<Livros />} />
      </Routes>
    </div>
  )
}

export default App
