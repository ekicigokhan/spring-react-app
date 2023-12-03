import { Spinner } from "./Spinner";

export function Button({ apiProgress, disabled, children }) {
  return (
    <button className="btn btn-warning " disabled={disabled || apiProgress}>
      {apiProgress && <Spinner sm />}
      {children}
    </button>
  );
}
