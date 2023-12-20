import { createBrowserRouter } from "react-router-dom";
import { SignUp } from "@/pages/SignUp";
import { Home } from "@/pages/Home";
import App from "@/App";
import { Activation } from "@/pages/Activation";
import { User } from "@/pages/User";
import { Login } from "@/pages/Login";
import { PasswordResetRequest } from "@/pages/PasswordReset/Request";
import { SetPassword } from "@/pages/PasswordReset/SetPassword";

const routes = createBrowserRouter([
  {
    path: "/",
    Component: App,
    children: [
      {
        path: "/",
        index: true,
        Component: Home,
      },
      {
        path: "/signup",
        Component: SignUp,
      },
      {
        path: "/login",
        Component: Login,
      },
      {
        path: "/activation/:token",
        Component: Activation,
      },
      {
        path: "/user/:id",
        Component: User,
      },
      {
        path: "/password-reset/request",
        Component: PasswordResetRequest,
      },
      {
        path: "/password-reset/set",
        Component: SetPassword,
      },
    ],
  },
]);

export default routes;
