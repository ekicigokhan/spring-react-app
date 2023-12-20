import { useTranslation } from "react-i18next";
import logo from "@/assets/hoaxify.png";
import { Link } from "react-router-dom";
import { useAuthDispatch, useAuthState } from "../../state/context";
import { ProfileImage } from "../ProfileImage";
import { logout } from "./api";
import { useState } from "react";

export function NavBar() {
  const { t } = useTranslation();
  const authState = useAuthState();
  const dispatch = useAuthDispatch();
  const [logoutInfo, setLogoutInfo] = useState();

  const onClickLogout = async () => {
    try {
      const response = await logout();
      setLogoutInfo(response.data.message);
    } catch {
    } finally {
      dispatch({ type: "logout-success" });
    }
  };
  return (
    <nav className="navbar navbar-expand bg-body-tertiary shadow-lg">
      <div className="container-fluid">
        <Link to="/" className="navbar-brand">
          <img src={logo} width={60}></img>
          Hoaxify
        </Link>
        <ul className="navbar-nav">
          {authState.id === 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/login">
                  {t("login")}
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/signup">
                  {t("signUp")}
                </Link>
              </li>
            </>
          )}
          {authState.id > 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to={`/user/${authState.id}`}>
                  <ProfileImage width={30} image={authState.image} />
                  <span className="ms-2">{authState.username}</span>
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" onClick={onClickLogout}>
                  Logout
                </Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}
