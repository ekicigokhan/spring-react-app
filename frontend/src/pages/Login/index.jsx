import { Input } from "@/shared/components/input";
import { useState } from "react";
import { useEffect } from "react";
import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { useTranslation } from "react-i18next";
import { login } from "./api";

export default function Login() {
  const { t } = useTranslation();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [apiProgress, setApiProgress] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();

  useEffect(() => {
    setErrors((lastErrors) => {
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors((lastErrors) => {
      return {
        ...lastErrors,
        password: undefined,
      };
    });
  }, [password]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setGeneralError();
    setApiProgress(true);

    try {
      const response = await login({ email, password });
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>{t("login")}</h1>
          </div>
          <div className="card-body">
            <Input
              id="username"
              label={t("email")}
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
              as
            />
            <Input
              id="email"
              label={t("password")}
              error={errors.password}
              type="password"
              onChange={(event) => setPassword(event.target.value)}
            />
          </div>
          <div className="card-footer text-center">
            {generalError && <Alert styleType="danger">{generalError}</Alert>}
            <Button disabled={!email || !password} apiProgress={apiProgress}>
              {t("login")}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}