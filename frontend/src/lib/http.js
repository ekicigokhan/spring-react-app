import axios from "axios";
import { i18nInstance } from "@/locales";
import { loadToken, storeToken } from "@/shared/state/storage";

const http = axios.create(); // axios instance

let authToken = loadToken(); // initialize ettim.

export function setToken(token) {
  //token = action.data.token
  authToken = token;
  storeToken(token); // token varsa localstorage'e yazılıyo yoksa da var olan logout senaryosudur.
}

http.interceptors.request.use((config) => {
  config.headers["Accept-Language"] = i18nInstance.language;
  if (authToken) {
    config.headers["Authorization"] = `${authToken.prefix} ${authToken.token}`;
  }
  return config;
});

export default http;
