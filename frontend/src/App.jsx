import { Route, Routes } from 'react-router-dom'
import './App.css'
import Cadastro from './pages/Cadastro'
import Login from './pages/Login'

function App() {
  
  return (
    <>
      <Routes>
        <Route path="/" element={<></>} />
        <Route path="/cadastrar" element={<Cadastro />} />
        <Route path="/logar" element={<Login />} />
      </Routes>
    </>
  )
}

export default App
