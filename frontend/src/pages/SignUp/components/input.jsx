export const Input = (props) => {
  return (
    <div className="mb-3">
      <label htmlFor={props.id} className="form-label">
        {props.label}
      </label>
      <input
        id={props.id}
        className={props.error ? "form-control is-invalid" : "form-control"}
        type="text"
        onChange={props.onChange}
      />
      <div className="invalid-feedback">{props.error}</div>
    </div>
  );
};
