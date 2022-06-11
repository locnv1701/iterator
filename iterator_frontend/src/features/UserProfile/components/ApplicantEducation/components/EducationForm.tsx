import { Box, makeStyles, TextField, Theme, createStyles } from '@material-ui/core';
import React from 'react';
import { Control, Controller } from 'react-hook-form';
import { IEducation, IUserInfo } from 'features/UserProfile/types/userProfileTypes';

interface Props {
  control: Control<IEducation, object>;
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

export const EducationForm = ({ control }: Props) => {
  const classes = useStyles();
  return (
    <Box className={classes.box}>
      {/*@ts-ignore*/}
      <Controller
        name="college_name"
        control={control}
        render={({ field }) => <TextField required {...field} fullWidth label="University" />}
      />
      <Controller
        name="major"
        control={control}
        render={({ field }) => <TextField required {...field} fullWidth label="Major" />}
      />
      <Controller
        name="cgpa"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Gpa" />}
      />
      <Controller
        name="starting_date"
        control={control}
        render={({ field }) => (
          <TextField
            {...field}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
            label="Starting Date"
            type="date"
          />
        )}
      />
      <Controller
        name="completion_date"
        control={control}
        render={({ field }) => (
          <TextField
            {...field}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
            label="Completion Date"
            type="date"
          />
        )}
      />
    </Box>
  );
};
