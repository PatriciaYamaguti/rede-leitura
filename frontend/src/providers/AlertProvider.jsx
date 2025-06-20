import { useState, useCallback } from "react";
import { AlertContext } from "../contexts/AlertContext";
import Alert from "../components/Alert";

export const AlertProvider = ({ children }) => {
  const [alert, setAlert] = useState(null);

  const showAlert = useCallback((mensagem, tipo = "sucesso") => {
    setAlert({ mensagem, tipo });
  }, []);

  const closeAlert = () => setAlert(null);

  return (
    <AlertContext.Provider value={{ showAlert }}>
      {children}
      {alert && (
        <Alert
          mensagem={alert.mensagem}
          tipo={alert.tipo}
          onClose={closeAlert}
        />
      )}
    </AlertContext.Provider>
  );
};