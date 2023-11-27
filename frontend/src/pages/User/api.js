import http from "@/lib/http";

export const getUser = (id) => {
  return http.get(`/api/v1/users/${id}`);
};
