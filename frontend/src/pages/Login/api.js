import http from "@/lib/http";

export function login(credintals) {
  return http.post("/api/v1/auth", credintals);
}
