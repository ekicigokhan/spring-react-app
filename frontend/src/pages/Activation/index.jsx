import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { activateUser } from "./api";
import { useState } from "react";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";

export const Activation = () => {
  const { token } = useParams();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [errorMessage, setErrorMessage] = useState();

  useEffect(() => {
    async function activate() {
      setApiProgress(true);
      try {
        const response = await activateUser(token);
        setSuccessMessage(response.data.message);
      } catch (axiosError) {
        setErrorMessage(axiosError.response.data.message);
      } finally {
        setApiProgress(false);
      }
    }
    activate();
  }, []);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {successMessage && <Alert styleType="success">{successMessage}</Alert>}
      {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
    </>
  );
};
