import { useTranslation } from "react-i18next";
import logo from "@/assets/hoaxify.png";
import { Link } from "react-router-dom";

export function NavBar() {
  const { t } = useTranslation();
  return (
    <nav className="navbar navbar-expand bg-body-tertiary shadow-lg">
      <div className="container-fluid">
        <Link to="/" className="navbar-brand">
          <img src={logo} width={60}></img>
          Hoaxify
        </Link>
        <ul className="navbar-nav">
          <li className="nav-item">
            <Link className="nav-link" to="/signup">
              {t("signUp")}
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}
