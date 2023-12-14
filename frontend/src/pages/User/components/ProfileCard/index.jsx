import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/input";
import { useAuthState } from "@/shared/state/context";
import { useState } from "react";
import { Alert } from "@/shared/components/Alert";
import { ProfileImage } from "@/shared/components/ProfileImage";
import { UserEditForm } from "./UserEditForm";

export function ProfileCard({ user }) {
  const authState = useAuthState();
  const [editMode, setEditMode] = useState(false);

  const isEditButtonVisible = !editMode && authState.id === user.id;
  const visibleUsername =
    authState.id === user.id ? authState.username : user.username;

  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImage width={200} />
      </div>
      <div className="card-body text-center">
        {!editMode && <span className="fs-3 d-block">{visibleUsername}</span>}
        {isEditButtonVisible && (
          <Button
            onClick={() => {
              setEditMode(true);
            }}
            children={"Edit"}
          />
        )}
        {editMode && <UserEditForm setEditMode={setEditMode} />}
      </div>
    </div>
  );
}
