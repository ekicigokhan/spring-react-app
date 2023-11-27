import { activateUser } from "./api";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";
import { useRouteParamApiRequest } from "@/shared/components/hooks/useRouteParamApiRequest";

export const Activation = () => {
  const {
    apiProgress: apiProgress,
    data: successMessage,
    error: errorMessage,
  } = useRouteParamApiRequest("token", activateUser);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {successMessage && (
        <Alert styleType="success">{successMessage.message}</Alert>
      )}
      {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
    </>
  );
};
