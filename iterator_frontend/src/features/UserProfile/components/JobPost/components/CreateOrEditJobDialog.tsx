import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { IJobPost } from 'features/UserProfile/types/userProfileTypes';
import { Box, Checkbox, FormControlLabel } from '@material-ui/core';
import { useStyles } from './styles';
import { Control, Controller, UseFormHandleSubmit } from 'react-hook-form';

interface Props {
  open: boolean;
  handleClose: () => void;
  control: Control<IJobPost, object>;
  onSubmit: (data: IJobPost) => void;
  handleSubmit: UseFormHandleSubmit<IJobPost>;
  modalType: number;
}

export default function CreateOrEditDialog({
  handleClose,
  open,
  modalType,
  control,
  onSubmit,
  handleSubmit,
}: Props) {
  const classes = useStyles();

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle id="form-dialog-title">{modalType ? 'Create New Job' : 'Edit Job'}</DialogTitle>
      <DialogContent>
        <form id="jobForm" onSubmit={handleSubmit(onSubmit)}>
          {/*@ts-ignore*/}
          <Controller
            name="company.company_name"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                margin="dense"
                variant="outlined"
                id="company-name"
                label="Company Name"
                fullWidth
                required
              />
            )}
          />

          <Controller
            name="company.business_stream"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                variant="outlined"
                margin="dense"
                id="business-stream"
                label="Business Stream"
                fullWidth
                required
              />
            )}
          />

          <Controller
            name="company.profile_description"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                margin="normal"
                variant="outlined"
                multiline
                rows={3}
                id="company-desc"
                label="Company Description"
                fullWidth
                required
              />
            )}
          />

          <Controller
            name="job_type"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                margin="dense"
                variant="outlined"
                id="position"
                label="Position"
                fullWidth
              />
            )}
          />

          <Controller
            name="job_description"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                margin="dense"
                variant="outlined"
                id="job-desc"
                label="Job Description"
                rows={4}
                multiline
                fullWidth
                required
              />
            )}
          />

          <Box className={classes.rowWithTwoCols}>
            <Controller
              name="job_salary"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  required
                  variant="outlined"
                  margin="dense"
                  id="salary"
                  type="number"
                  label="Salary"
                />
              )}
            />
            <Controller
              name="job_location"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  required
                  variant="outlined"
                  margin="dense"
                  id="location"
                  label="Location"
                />
              )}
            />
          </Box>
          <Controller
            name="is_available"
            control={control}
            render={({ field }) => (
              <FormControlLabel
                control={
                  <Checkbox {...field} color="primary" className={classes.checkboxControl} />
                }
                label="The job will be active once created"
              />
            )}
          />
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="secondary">
          Cancel
        </Button>
        <Button form="jobForm" type="submit" color="primary">
          Submit
        </Button>
      </DialogActions>
    </Dialog>
  );
}
