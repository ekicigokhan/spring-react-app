import { ProfileImage } from "@/shared/components/ProfileImage";
import { Link } from "react-router-dom";

export function UserListItem({ user }) {
  return (
    <Link
      className="list-group-item list-group-item-action nav-link"
      to={`/user/${user.id}`}
    >
      <ProfileImage width={30} image={user.image} />
      <span className="ms-2">{user.username}</span>
    </Link>
  );
}
