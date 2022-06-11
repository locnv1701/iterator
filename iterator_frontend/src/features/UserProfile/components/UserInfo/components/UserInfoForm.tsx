import { Box, makeStyles, TextField, Theme, createStyles } from '@material-ui/core';
import React from 'react';
import { Control, Controller } from 'react-hook-form';
import { IUserInfo } from 'features/UserProfile/types/userProfileTypes';

interface Props {
  control: Control<IUserInfo, object>;
}

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    box: {
      '& > *': {
        marginBottom: theme.spacing(2),
      },
    },
  }),
);

export const UserInfoForm = ({ control }: Props) => {
  const classes = useStyles();
  return (
    <Box className={classes.box}>
      {/*@ts-ignore*/}
      <Controller
        name="first_name"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="First Name" />}
      />
      <Controller
        name="last_name"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Last Name" />}
      />
      <Controller
        name="email"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Email" />}
      />
      <Controller
        name="phone_number"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Phone Number" />}
      />
      <Controller
        name="location"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Location" />}
      />
      <Controller
        name="entry_level"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Position" />}
      />
    </Box>
  );
};
