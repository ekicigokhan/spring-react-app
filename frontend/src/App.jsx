import { Outlet } from "react-router-dom";
import { LanguageSelector } from "@/shared/components/LanguageSelector";
import { NavBar } from "@/shared/components/NavBar";
import { Link } from "react-router-dom";

function App() {
  return (
    <>
      <NavBar />
      <div className="container mt-3">
        <Outlet />
        <LanguageSelector />
      </div>
    </>
  );
}

export default App;
