import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/input";
import { usePasswordResetRequest } from "./usePasswordResetRequest";

export function PasswordResetRequest() {
  const {
    onChangeEmail,
    onSubmit,
    success,
    apiProgress,
    error,
    generalError,
    email,
  } = usePasswordResetRequest();

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>Reset your password</h1>
          </div>
          <div className="card-body">
            <Input
              id="email"
              label="E-mail"
              error={error}
              onChange={onChangeEmail}
            />
          </div>
          <div className="card-footer text-center">
            {success && <Alert>{success}</Alert>}
            {generalError && <Alert styleType="danger">{generalError}</Alert>}
            <Button apiProgress={apiProgress}>{"Reset"}</Button>
          </div>
        </form>
      </div>
    </div>
  );
}
