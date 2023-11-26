import http from "@/lib/http";

export const activateUser = (token) => {
  return http.patch(`/api/v1/users/${token}/active`);
};
