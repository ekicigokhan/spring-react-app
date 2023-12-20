import { useCallback } from "react";
import { useState } from "react";
import { passwordResetRequest } from "./api";

export function usePasswordResetRequest() {
  const [success, setSuccess] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [error, setError] = useState({});
  const [generalError, setGeneralError] = useState();
  const [email, setEmail] = useState();

  const onSubmit = useCallback(
    async (event) => {
      event.preventDefault();
      setGeneralError();
      setSuccess();
      setError({});
      setApiProgress(true);

      try {
        const response = await passwordResetRequest({ email });
        setSuccess(response.data.message);
      } catch (axiosError) {
        if (axiosError.response.data.status === 400) {
          setError(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } finally {
        setApiProgress(false);
      }
    },
    [email]
  );

  return {
    onChangeEmail: (event) => setEmail(event.target.value),
    onSubmit,
    success,
    apiProgress,
    error: error.email,
    generalError,
  };
}
