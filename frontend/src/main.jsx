import React from "react";
import ReactDOM from "react-dom/client";
import "@/styles.scss";
import "@/locales";
import { RouterProvider } from "react-router-dom";
import routes from "@/routes/index.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <RouterProvider router={routes} />
);
