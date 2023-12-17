import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/input";
import { useAuthDispatch, useAuthState } from "@/shared/state/context";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { updateUser } from "./api";

export function UserEditForm({ setEditMode, setTempImage }) {
  const authState = useAuthState();
  const { t } = useTranslation();
  const [newUserName, setNewUserName] = useState(authState.username);
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  const [newImage, setNewImage] = useState();

  const dispatch = useAuthDispatch();

  const onChangeUserName = (event) => {
    setNewUserName(event.target.value);
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        username: undefined,
      };
    });
  };

  const onSelectImage = (event) => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        image: undefined,
      };
    });

    if (event.target.files.length < 1) return;
    const file = event.target.files[0];
    const fileReader = new FileReader();

    fileReader.onloadend = () => {
      const data = fileReader.result; //seçtiğimiz dosyanın string haline erişebiliyoruz. implement ettik.
      setNewImage(data);
      setTempImage(data);
    };

    fileReader.readAsDataURL(file); //async yürüyor ve işlem tamamlandığında fileReader'ın onloadend'i çağıracak biz onu implement edeceğiz.
  };

  const onClickCancel = () => {
    setEditMode(false);
    setNewUserName(authState.username);
    setNewImage();
    setTempImage();
  };

  const onSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      const { data } = await updateUser(authState.id, {
        username: newUserName,
        image: newImage,
      });
      dispatch({
        type: "user-update-success",
        data: { username: data.username, image: data.image },
      });
      setEditMode(false);
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <form onSubmit={onSubmit}>
      <Input
        label={t("username")}
        defaultValue={authState.username}
        onChange={onChangeUserName}
        error={errors.username}
      />
      <Input
        label="Profile Image"
        type="file"
        onChange={onSelectImage}
        error={errors.image}
      />
      {generalError && <Alert styleType="danger">{generalError}</Alert>}
      <Button apiProgress={apiProgress} children={"Save"} type={"submit"} />
      <Button
        styleType="outline-secondary"
        onClick={onClickCancel}
        children={"Cancel"}
        type="button"
      />
    </form>
  );
}
