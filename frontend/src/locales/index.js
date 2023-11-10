import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import en from "./translations/en.json";
import tr from "./translations/tr.json";

const initialLanguage =
  localStorage.getItem("lang") || navigator.language || "en";

export const i18nInstance = i18n.use(initReactI18next); // react versiyonunu verdik

i18nInstance.init({
  resources: {
    // initialize sonucunda dille ilgili desteklerin neler oldugunu yazdık.
    en: {
      translation: en,
    },
    tr: {
      translation: tr,
    },
  },
  fallbackLng: initialLanguage, // desteklenmeyen dil seçilirse yine : eng veya browser dili

  interpolation: {
    escapeValue: false,
  },
});
