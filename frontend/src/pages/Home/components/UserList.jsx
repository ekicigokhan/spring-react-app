import { useState } from "react";
import { loadUsers } from "./api";
import { useEffect } from "react";
import { useCallback } from "react";
import { Spinner } from "@/shared/components/Spinner";
import { UserListItem } from "./UserListItem";

export function UserList() {
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    first: false,
    number: 0,
  });
  const [apiProgress, setApiProgress] = useState(true);

  const getUsers = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await loadUsers(page);
      setUserPage(response.data);
    } catch {
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    getUsers();
  }, []);

  return (
    <div className="card ">
      <div className="card-header text-center fs-4">User List</div>
      <ul className="list-group list-group-flush">
        {userPage.content.map((user) => {
          return <UserListItem key={user.id} user={user} />;
        })}
      </ul>

      <div className="card-footer text-body-secondary text-center">
        {apiProgress && <Spinner sm />}
        {!apiProgress && !userPage.first && (
          <button
            className="btn btn-outline-secondary btn-sm float-start"
            onClick={() => {
              getUsers(userPage.number - 1);
            }}
          >
            Previous
          </button>
        )}

        {!apiProgress && !userPage.last && (
          <button
            className="btn btn-outline-secondary btn-sm float-end"
            onClick={() => {
              getUsers(userPage.number + 1);
            }}
          >
            Next
          </button>
        )}
      </div>
    </div>
  );
}
