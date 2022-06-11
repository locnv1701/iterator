import React, { useState } from 'react';
import { BorderLinearProgress, useStyles } from './styles';
import { Paper, Typography } from '@material-ui/core';
import { CancelButton, EditButton } from 'features/UserProfile/styles';
import { ISkill } from 'features/UserProfile/types/userProfileTypes';
import { useForm } from 'react-hook-form';
import { FieldArray } from './components/FieldArray';
import { useDispatch } from 'react-redux';
import { editApplicantSkillSet } from 'features/UserProfile/store/userProfileSlices';

interface Props {
  skillSet?: ISkill[];
  applicantId: number;
  hideEditButton?: boolean;
}

export interface ISkillSet {
  skills: ISkill[];
}

export const SkillSet = ({ skillSet, applicantId, hideEditButton }: Props) => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [isEdit, setIsEdit] = useState(false);
  const { control, handleSubmit, reset } = useForm<ISkillSet>({
    defaultValues: { skills: skillSet },
  });
  const onSubmit = (data: ISkillSet) => {
    setIsEdit(false);
    dispatch(editApplicantSkillSet({ skillSet: data.skills, applicantId: applicantId }));
  };
  const clickEditButton = () => {
    setIsEdit(true);
    reset({ skills: skillSet });
  };
  return (
    <Paper elevation={3} className={classes.paper}>
      <Typography className={classes.title}>Skills</Typography>
      {isEdit ? (
        <form id="skillSet" onSubmit={handleSubmit(onSubmit)}>
          <FieldArray control={control} />
        </form>
      ) : (
        skillSet?.map((item) => (
          <>
            <Typography>{item.skill_name}</Typography>
            <BorderLinearProgress variant="determinate" value={item.level * 10} />
          </>
        ))
      )}
      {isEdit ? (
        <>
          <CancelButton onClick={() => setIsEdit(false)} variant="contained">
            Cancel
          </CancelButton>
          <EditButton type="submit" form="skillSet" variant="contained">
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
