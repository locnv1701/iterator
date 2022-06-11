/* eslint-disable indent */
import {
  Box,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
} from '@material-ui/core';
import { positionList } from 'features/Register/Register';
import { EditButton } from 'features/UserProfile/styles';
import React, { useEffect, useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import {
  getCurrentApplicantPage,
  getCurrentPage,
  setCurrentApplicantPage,
  setCurrentPage,
} from 'utils/localStorage';
import Deck from './components/Deck';
import { getApplicantList, getJobPosts } from './store/homeSlice';
import { useStyles } from './styles';
import { IApplicantQuery, IJobPostQuery } from './types/homeTypes';

interface FormValue {
  jobFilterId: number;
  experience: number;
  position: string;
  job_salary: number;
  job_location: string;
  job_type: string;
}

const HomePage = () => {
  const classes = useStyles();
  const dispatch = useAppDispatch();
  const { userId, applicantId } = useAppSelector((state) => state.authLogin);
  const jobList = useAppSelector((state) => state.profile.sharerJobPost);
  const jobs = useAppSelector((state) => state.home.jobList);
  const applicantList = useAppSelector((state) => state.home.applicantList);
  const cards = !!applicantId
    ? [...new Array(jobs.length)].map((index) => index + 1)
    : [...new Array(applicantList.length)].map((index) => index + 1);
  const [applicantQuery, setApplicantQuery] = useState<IApplicantQuery>();
  const [jobPostQuery, setJobPostQuery] = useState<IJobPostQuery>();

  const [requestedJobId, setRequestedJobId] = useState<number>();

  const { control, handleSubmit } = useForm<FormValue>();

  const onSubmit: SubmitHandler<FormValue> = (data) => {
    if (!applicantId) {
      const body =
        !!data.experience || !!data.position
          ? { entry_level: data.position, experience_year: data.experience }
          : undefined;
      dispatch(
        getApplicantList({
          userId: userId,
          page: Number(getCurrentApplicantPage(String(data.jobFilterId))),
          jobId: data.jobFilterId,
          body,
        }),
      );
      setRequestedJobId(data.jobFilterId);
      setApplicantQuery(body);
    } else {
      const body =
        !!data.job_type || !!data.job_salary || !!data.job_location
          ? {
              job_type: data.job_type === '' ? undefined : data.job_type,
              job_salary: !!data.job_salary ? Number(data.job_salary) : undefined,
              job_location: data.job_location === '' ? undefined : data.job_location,
            }
          : undefined;
      const currentPage = Number(getCurrentPage(String(userId)));
      dispatch(getJobPosts({ data: currentPage, userId: userId, body }));
      setJobPostQuery(body);
    }
  };

  useEffect(() => {
    applicantId &&
      dispatch(getJobPosts({ data: Number(getCurrentPage(String(userId))), userId: userId }));
  }, [dispatch, userId, applicantId]);

  return (
    <Box display="flex" height="100%">
      <Box height="100%" width="22%" p={2} boxSizing="border-box" className={classes.filterWrapper}>
        <Typography variant="h4">Filter Settings</Typography>
        <form onSubmit={handleSubmit(onSubmit)}>
          {!applicantId ? (
            <>
              {/*@ts-ignore*/}
              <Controller
                name="jobFilterId"
                control={control}
                render={({ field }) => (
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel id="demo-simple-select-outlined-label">Select Job</InputLabel>
                    <Select
                      {...field}
                      labelId="demo-simple-select-outlined-label"
                      id="demo-simple-select-outlined"
                      label="Select Job"
                    >
                      {jobList?.map(
                        (job) =>
                          job.is_available && (
                            <MenuItem key={job.job_post_id} value={job.job_post_id}>
                              {`${job.company.company_name} - ${job.job_type}`}
                            </MenuItem>
                          ),
                      )}
                    </Select>
                  </FormControl>
                )}
              />
              <Box mt={3}>
                <Controller
                  name="position"
                  control={control}
                  render={({ field }) => (
                    <FormControl variant="outlined" fullWidth>
                      <InputLabel id="demo-simple-select-outlined-label1">Position</InputLabel>
                      <Select
                        {...field}
                        labelId="demo-simple-select-outlined-label1"
                        id="demo-simple-select-outlined"
                        label="Position"
                      >
                        <MenuItem value="">
                          <em>None</em>
                        </MenuItem>
                        {positionList.map((position, index) => (
                          <MenuItem key={index} value={position}>
                            {position}
                          </MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  )}
                />
              </Box>
              <Box mt={3}>
                <Controller
                  name="experience"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      type="number"
                      variant="outlined"
                      label="Experience"
                      fullWidth
                    />
                  )}
                />
              </Box>
            </>
          ) : (
            <>
              <Box>
                <Controller
                  name="job_type"
                  control={control}
                  render={({ field }) => (
                    <TextField {...field} variant="outlined" label="Job type" fullWidth />
                  )}
                />
              </Box>
              <Box mt={3}>
                <Controller
                  name="job_salary"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      {...field}
                      type="number"
                      variant="outlined"
                      label="Job Salary"
                      fullWidth
                    />
                  )}
                />
              </Box>
              <Box mt={3}>
                <Controller
                  name="job_location"
                  control={control}
                  render={({ field }) => (
                    <TextField {...field} variant="outlined" label="Job Location" fullWidth />
                  )}
                />
              </Box>
            </>
          )}
          <Box mt={2} textAlign="right">
            <EditButton type="submit">Apply</EditButton>
          </Box>
        </form>
      </Box>
      {!!applicantId && !!cards.length ? <Deck cards={cards} /> : <></>}
      {!applicantId && !!requestedJobId && !!cards.length ? (
        <Deck cards={cards} requestJobId={requestedJobId} />
      ) : (
        <></>
      )}
      <EditButton
        onClick={() => {
          if (!!applicantId) {
            const nextPage = Number(getCurrentPage(String(userId))) + 1;
            dispatch(getJobPosts({ data: nextPage, userId: userId, body: jobPostQuery }));
            setCurrentPage(String(userId), String(nextPage));
          } else {
            const nextPage = Number(getCurrentApplicantPage(String(requestedJobId))) + 1;
            dispatch(
              getApplicantList({
                userId: userId,
                page: nextPage,
                jobId: requestedJobId,
                body: applicantQuery,
              }),
            );
            setCurrentApplicantPage(String(requestedJobId), String(nextPage));
          }
        }}
        style={{ position: 'fixed', bottom: 30, right: 30 }}
      >
        Get More
      </EditButton>
    </Box>
  );
};

export default HomePage;
