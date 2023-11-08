import { useEffect, useState } from "react";
import { signUp } from "./api";
import { Input } from "./components/input";

export const SignUp = () => {
  const [username, setUserName] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();

  useEffect(() => {
    setErrors((lastErrors) => {
      return {
        ...lastErrors, // response.data.validationErrors 'u çoğalttım ve çoğalttığım objeyi güncelleyip return ettim.
        username: undefined,
      };
    });
  }, [username]); //username her değiştiğinde içerideki function çalışacak.

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
    event.preventDefault(); //Browser tarafından gerçekleşecek reload'ı engelledim.
    setApiProgress(true);
    setSuccessMessage();
    setGeneralError();

    try {
      const response = await signUp({
        username: username,
        email: email,
        password: password,
      });
      setSuccessMessage(response.data.message);
    } catch (axiosError) {
      if (
        axiosError.response?.data &&
        axiosError.response.data.status === 400
      ) {
        setErrors(axiosError.response.data.validationErrors);
      } else {
        setGeneralError("Unexpected error occured. Please try again.");
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
            <h1>Sign Up</h1>
          </div>
          <div className="card-body">
            <Input
              id="username"
              label="Username"
              error={errors.username}
              onChange={(event) => setUserName(event.target.value)}
            />
            <Input
              id="email"
              label="E-mail"
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
            />
            <Input
              id="password"
              label="Password"
              type="password"
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
            />
            <Input
              id="username"
              label="Password Repeat"
              onChange={(event) => setPasswordRepeat(event.target.value)}
            />
          </div>

          <div className="text-center card-footer">
            {successMessage && (
              <div className="alert alert-success">{successMessage}</div>
            )}
            {generalError && (
              <div className="alert alert-danger">{generalError}</div>
            )}
            <button
              className="btn btn-warning"
              disabled={!password || password !== passwordRepeat || apiProgress}
            >
              Sign Up
              {apiProgress && (
                <span
                  className="spinner-border spinner-border-sm"
                  aria-hidden="true"
                ></span>
              )}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
