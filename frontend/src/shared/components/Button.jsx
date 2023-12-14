import { Spinner } from "./Spinner";

export function Button({
  apiProgress,
  disabled,
  children,
  onClick,
  styleType = "primary",
  type,
}) {
  return (
    <button
      className={`btn btn-${styleType} m-1`}
      disabled={disabled || apiProgress}
      onClick={onClick}
      type={type}
    >
      {apiProgress && <Spinner sm />}
      {children}
    </button>
  );
}
