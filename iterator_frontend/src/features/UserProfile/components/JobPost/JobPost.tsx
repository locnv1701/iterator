import React, { useState } from 'react';

import Typography from '@material-ui/core/Typography';
import { Accordion, AccordionDetails, AccordionSummary, useStyles } from './styles';
import { Box, Chip, IconButton, Paper } from '@material-ui/core';
import { IJobPost } from 'features/UserProfile/types/userProfileTypes';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import { EditButton } from 'features/UserProfile/styles';
import CreateOrEditDialog from './components/CreateOrEditJobDialog';
import { useAppDispatch } from 'store/hooks';
import {
  createJobPost,
  deleteJobPost,
  editJobPost,
} from 'features/UserProfile/store/userProfileSlices';
import { useForm } from 'react-hook-form';

interface Props {
  jobsPost: IJobPost[];
  userId: number;
  hideEditButton?: boolean;
}

export default function JobPost({ jobsPost, userId, hideEditButton }: Props) {
  const classes = useStyles();
  const dispatch = useAppDispatch();
  const [expanded, setExpanded] = useState<number | boolean>(false);

  const [modalType, setModalType] = useState(0);
  const defaultValues: IJobPost = {
    job_type: '',
    job_description: '',
    job_location: '',
    job_salary: NaN,
    sharer_id: userId,
    is_available: false,
    company: {
      company_name: '',
      profile_description: '',
      business_stream: '',
    },
  };
  const { handleSubmit, control, reset } = useForm<IJobPost>();
  const onSubmit = (data: IJobPost) => {
    handleClose();
    dispatch(
      modalType
        ? createJobPost({ ...data, sharer_id: userId })
        : editJobPost({ ...data, sharer_id: userId }),
    );
  };

  const handleChange = (id?: number) => (event: React.ChangeEvent<{}>, newExpanded: boolean) => {
    id && setExpanded(newExpanded ? id : false);
  };

  const [open, setOpen] = useState(false);

  const handleClickOpen = (job: IJobPost, modalType: number) => {
    setOpen(true);
    reset(job);
    setModalType(modalType);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Paper elevation={3} className={classes.paper}>
      <Typography className={classes.title}>Recruitment</Typography>
      {jobsPost.map((item) => (
        <Accordion
          key={item.job_post_id}
          square
          expanded={expanded === item.job_post_id}
          onChange={handleChange(item.job_post_id)}
        >
          <AccordionSummary>
            <Box>
              <Typography className={classes.jobType}>{item.company.company_name}</Typography>
              <Typography>{item.job_type}</Typography>
              <Box className={classes.chipList}>
                <Chip
                  className={item.is_available ? classes.activeChip : ''}
                  size="small"
                  label={item.is_available ? 'Active' : 'Inactive'}
                />
                <Chip
                  icon={<AttachMoneyIcon />}
                  size="small"
                  color="primary"
                  label={item.job_salary}
                />
                <Chip
                  icon={<LocationOnIcon />}
                  size="small"
                  color="secondary"
                  label={
                    <Box maxWidth={50} textOverflow="ellipsis">
                      {item.job_location}
                    </Box>
                  }
                />
              </Box>
            </Box>
            <Box>
              <IconButton onClick={() => handleClickOpen(item, 0)}>
                <EditIcon color="primary" />
              </IconButton>
              <IconButton
                onClick={() =>
                  dispatch(deleteJobPost({ userId: userId, jobPostId: item.job_post_id }))
                }
              >
                <DeleteIcon color="error" />
              </IconButton>
            </Box>
          </AccordionSummary>
          <AccordionDetails>
            <Typography color="textSecondary">{item.job_description}</Typography>
          </AccordionDetails>
        </Accordion>
      ))}
      {!hideEditButton && (
        <EditButton onClick={() => handleClickOpen(defaultValues, 1)}>Create New Job</EditButton>
      )}
      <CreateOrEditDialog
        open={open}
        modalType={modalType}
        handleClose={handleClose}
        handleSubmit={handleSubmit}
        onSubmit={onSubmit}
        control={control}
      />
    </Paper>
  );
}
