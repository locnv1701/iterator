import { Divider, Grid, Paper, Typography } from '@material-ui/core';
import { CancelButton, EditButton } from 'features/UserProfile/styles';
import React, { useState } from 'react';
import { useStyles } from './styles';
import { useForm } from 'react-hook-form';
import { IEducation } from 'features/UserProfile/types/userProfileTypes';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { editApplicantEducation } from 'features/UserProfile/store/userProfileSlices';
import { RootState } from 'store/store';
import { EducationForm } from './components/EducationForm';

interface Props {
  title?: string;
  content: {
    label: string;
    value?: string | number;
  }[];
  applicantId: number;
  hideEditButton?: boolean;
}

export const ApplicantEducation = ({ title, content, applicantId, hideEditButton }: Props) => {
  const classes = useStyles();
  const [isEdit, setIsEdit] = useState(false);
  const dispatch = useAppDispatch();
  const { education } = useAppSelector((state: RootState) => state.profile);

  const { handleSubmit, control, reset } = useForm<IEducation>({
    defaultValues: education,
  });

  const clickEditButton = () => {
    reset(education);
    setIsEdit(true);
  };
  const onEditEducation = (data: IEducation) => {
    dispatch(editApplicantEducation({ education: data, applicantId: applicantId }));
    setIsEdit(false);
  };
  return (
    <Paper elevation={3} className={classes.paper}>
      {title && <Typography className={classes.title}>{title}</Typography>}
      {isEdit ? (
        <form id="education" onSubmit={handleSubmit(onEditEducation)}>
          <EducationForm control={control} />
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
          <EditButton type="submit" form="education" variant="contained">
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
