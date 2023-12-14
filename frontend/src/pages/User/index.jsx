import { useParams } from "react-router-dom";
import { getUser } from "./api";
import { Spinner } from "@/shared/components/Spinner";
import { Alert } from "@/shared/components/Alert";
import { useState } from "react";
import { useEffect } from "react";
import { useRouteParamApiRequest } from "@/shared/components/hooks/useRouteParamApiRequest";
import { ProfileCard } from "./components/ProfileCard";

export function User() {
  const {
    apiProgress: apiProgress,
    data: user,
    error: error,
  } = useRouteParamApiRequest("id", getUser);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {user && <ProfileCard user={user} />}
      {error && <Alert styleType="danger">{error}</Alert>}
    </>
  );
}
