export const Input = (props) => {
  return (
    <div className="mb-3">
      <label htmlFor={props.id} className="form-label">
        {props.label}
      </label>
      <input
        id={props.id}
        className={props.error ? "form-control is-invalid" : "form-control"}
        type={props.type}
        onChange={props.onChange}
        defaultValue={props.defaultValue}
      />
      <div className="invalid-feedback">{props.error}</div>
    </div>
  );
};
