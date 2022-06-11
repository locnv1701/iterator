import { Divider, Grid, Paper, Typography } from '@material-ui/core';
import { CancelButton, EditButton } from 'features/UserProfile/styles';
import React, { useState } from 'react';
import { useStyles } from './styles';
import { useForm } from 'react-hook-form';
import { IEducation, IProfile, IUserInfo } from 'features/UserProfile/types/userProfileTypes';
import { UserInfoForm } from './components/UserInfoForm';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { editUserProfile } from 'features/UserProfile/store/userProfileSlices';
import { RootState } from 'store/store';

interface Props {
  title?: string;
  content: {
    label: string;
    value?: string | number;
  }[];
  userId: number;
  hideEditButton?: boolean;
}

export const UserInfo = ({ title, content, userId, hideEditButton }: Props) => {
  const classes = useStyles();
  const [isEdit, setIsEdit] = useState(false);
  const dispatch = useAppDispatch();
  const { userProfile } = useAppSelector((state: RootState) => state.profile);
  const defaultValues = {
    email: userProfile.email,
    phone_number: userProfile.phone_number,
    first_name: userProfile.first_name,
    last_name: userProfile.last_name,
    location: userProfile.location,
    entry_level: userProfile.entry_level,
  };

  const { handleSubmit, control, reset } = useForm<IUserInfo>({
    defaultValues: defaultValues,
  });

  const clickEditButton = () => {
    reset(defaultValues);
    setIsEdit(true);
  };
  const onEditUserInfo = (data: IUserInfo) => {
    const newUserProfile: IProfile = {
      ...userProfile,
      ...data,
      profile_display_name: '',
      age: 22,
      gender: 'MALE',
      status: 'ACTIVE',
    };
    dispatch(
      editUserProfile({
        profile: newUserProfile,
        userId,
      }),
    );
    setIsEdit(false);
  };
  return (
    <Paper elevation={3} className={classes.paper}>
      {title && <Typography className={classes.title}>{title}</Typography>}
      {isEdit ? (
        <form id="userInfo" onSubmit={handleSubmit(onEditUserInfo)}>
          <UserInfoForm control={control} />
        </form>
      ) : (
        content.map(
          (item) =>
            item.value !== '' &&
            item.value && (
              <>
                <Grid container>
                  <Grid container className={classes.label} justifyContent="flex-start" item xs={3}>
                    {item.label}
                  </Grid>
                  <Grid container justifyContent="flex-start" item xs={9}>
                    <Typography color="textSecondary">{item.value}</Typography>
                  </Grid>
                </Grid>
                <Divider />
              </>
            ),
        )
      )}
      {isEdit ? (
        <>
          <CancelButton onClick={() => setIsEdit(false)} variant="contained">
            Cancel
          </CancelButton>
          <EditButton type="submit" form="userInfo" variant="contained">
            Confirm
          </EditButton>
        </>
      ) : (
        !hideEditButton && (
          <EditButton onClick={clickEditButton} variant="contained">
            Edit
          </EditButton>
        )
      )}
    </Paper>
  );
};
