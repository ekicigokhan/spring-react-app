import { useState } from "react";
import { signUp } from "./api";

export const SignUp = () => {
  const [username, setUserName] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();

  const onSubmit = async (event) => {
    event.preventDefault(); //Browser tarafından gerçekleşecek reload'ı engelledim.
    setApiProgress(true);
    setSuccessMessage();
    try {
      const response = await signUp({
        username: username,
        email: email,
        password: password,
      });
      setSuccessMessage(response.data.apiError);
    } catch (error) {
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
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <input
                id="username"
                className="form-control"
                type="text"
                onChange={(event) => setUserName(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                value={email}
                id="email"
                className="form-control"
                type="text"
                onChange={(event) => setEmail(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                value={password}
                id="password"
                className="form-control"
                type="text"
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="passwordRepeat" className="form-label">
                Password Repeat
              </label>
              <input
                value={passwordRepeat}
                id="passwordRepeat"
                className="form-control"
                type="text"
                onChange={(event) => setPasswordRepeat(event.target.value)}
              />
            </div>
          </div>

          <div className="text-center card-footer">
            {successMessage && (
              <div className="alert alert-success">{successMessage}</div>
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
