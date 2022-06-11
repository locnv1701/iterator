import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import API from 'api/api';
import { toast } from 'react-toastify';
import { IJobPost } from '../../UserProfile/types/userProfileTypes';
import { IApplicantCard, IApplicantQuery, IJobPostQuery } from '../types/homeTypes';

export interface HomeState {
  jobList: IJobPost[];
  applicantList: IApplicantCard[];
}

export const getJobPosts = createAsyncThunk(
  'applicant/getJobPost',
  async ({ data, userId, body }: { data?: number; userId: number; body?: IJobPostQuery }) => {
    const page = data || 0;
    const res = await API.post(`/api/display/sharer/job-post/${userId}?page=${page}&size=4`, body);
    return res.data;
  },
);

export const sendMatchRequest = createAsyncThunk(
  'user/sendMatchRequest',
  async ({ userId, target }: any) => {
    const res = await API.post(`/api/upcoming-request/${userId}`, target);
    return res;
  },
);

export const getApplicantList = createAsyncThunk(
  'sharer/getApplicants',
  async ({
    userId,
    page,
    jobId,
    body,
  }: {
    page: number;
    userId: number;
    jobId?: number;
    body?: IApplicantQuery;
  }) => {
    const res = await API.post(
      `/api/display/applicant/${userId}?page=${page}&size=4&job_post_id=${jobId}`,
      body,
    );
    return res.data;
  },
);

const initialState: HomeState = {
  jobList: [],
  applicantList: [],
};

export const homeSlice = createSlice({
  name: 'home',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getJobPosts.fulfilled, (state, action) => {
      state.jobList = action.payload;
    });
    builder.addCase(sendMatchRequest.fulfilled, () => {
      toast.info('Liked!!!', {
        position: toast.POSITION.TOP_RIGHT,
      });
    });
    builder.addCase(getApplicantList.fulfilled, (state, action) => {
      state.applicantList = action.payload;
    });
  },
});

export const {} = homeSlice.actions;

export default homeSlice.reducer;
