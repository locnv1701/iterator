import { Box, Paper, Typography } from '@material-ui/core';
import React, { useState } from 'react';
import { useStyles } from './styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { CancelButton, EditButton } from 'features/UserProfile/styles';
import { IExperience } from 'features/UserProfile/types/userProfileTypes';
import { useForm } from 'react-hook-form';
import { FieldArray } from './components/FieldArray';
import { useAppDispatch } from 'store/hooks';
import { editApplicantExperience } from 'features/UserProfile/store/userProfileSlices';

interface Props {
  experiences?: IExperience[];
  applicantId: number;
  hideEditButton?: boolean;
}

export interface IExperiences {
  experiences: IExperience[];
}

export const UserExperience = ({ experiences, applicantId, hideEditButton }: Props) => {
  const classes = useStyles();
  const dispatch = useAppDispatch();
  const [isEditing, setIsEditing] = useState(false);
  const { control, handleSubmit, reset } = useForm<IExperiences>({
    defaultValues: { experiences: experiences },
  });
  const clickEditButton = () => {
    setIsEditing(true);
    reset({ experiences: experiences });
  };
  const onSubmit = (data: IExperiences) => {
    setIsEditing(false);
    dispatch(editApplicantExperience({ experiences: data.experiences, applicantId: applicantId }));
  };
  const customDate = (date: string) => {
    let today = new Date(date);
    let dd = today.getDate().toString();
    let mm = (today.getMonth() + 1).toString();

    let yyyy = today.getFullYear();
    if (Number(dd) < 10) {
      dd = `0${dd}`;
    }
    if (Number(mm) < 10) {
      mm = `0${mm}`;
    }
    return `${dd}/${mm}/${yyyy}`;
  };
  return (
    <Paper elevation={3} className={classes.paper}>
      <Box className={classes.title}>
        <Typography>Professional Experience</Typography>
        {isEditing ? (
          <Box>
            <CancelButton onClick={() => setIsEditing(false)} variant="contained">
              Cancel
            </CancelButton>
            <EditButton type="submit" form="experiences" variant="contained">
              Confirm
            </EditButton>
          </Box>
        ) : (
          !hideEditButton && (
            <EditButton onClick={clickEditButton} variant="contained">
              Edit
            </EditButton>
          )
        )}
      </Box>
      <div className={classes.root}>
        {isEditing ? (
          <form id="experiences" onSubmit={handleSubmit(onSubmit)}>
            <FieldArray control={control} />
          </form>
        ) : (
          experiences?.map((item, index) => (
            <Accordion key={index}>
              <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <div className={classes.column}>
                  <Typography className={classes.heading}>{item.job_title}</Typography>
                </div>
                <div className={classes.column}>
                  <Typography className={classes.heading}>{item.company_name}</Typography>
                </div>
                <div className={classes.column}>
                  <Typography className={classes.heading}>
                    {customDate(item.start_date)} - {customDate(item.end_date)}
                  </Typography>
                </div>
              </AccordionSummary>
              <AccordionDetails>
                <Typography color="textSecondary">{item.project_self_description}</Typography>
              </AccordionDetails>
            </Accordion>
          ))
        )}
      </div>
    </Paper>
  );
};
