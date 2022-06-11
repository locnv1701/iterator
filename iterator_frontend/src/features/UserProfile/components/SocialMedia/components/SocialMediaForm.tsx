import { TextField } from '@material-ui/core';
import React from 'react';
import { Control, Controller } from 'react-hook-form';
import { ISocialMedia } from 'features/UserProfile/types/userProfileTypes';

interface Props {
  control: Control<ISocialMedia, object>;
}

export const SocialMediaForm = ({ control }: Props) => {
  return (
    <>
      {/*@ts-ignore*/}
      <Controller
        name="github"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Github" />}
      />
      <Controller
        name="twitter"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Twitter" />}
      />
      <Controller
        name="instagram"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Instagram" />}
      />
      <Controller
        name="facebook"
        control={control}
        render={({ field }) => <TextField {...field} fullWidth label="Facebook" />}
      />
    </>
  );
};
