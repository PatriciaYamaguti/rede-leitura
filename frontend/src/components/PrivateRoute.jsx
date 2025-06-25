import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
  const idUsuario = sessionStorage.getItem("idUsuario");

  if (!idUsuario) {
    return <Navigate to="/logar" />;
  }

  return children;
};

export default PrivateRoute;
