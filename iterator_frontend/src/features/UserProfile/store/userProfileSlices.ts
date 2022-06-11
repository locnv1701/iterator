import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import API from 'api/api';
import { toast } from 'react-toastify';
import {
  IProfile,
  IProfileState,
  IEducation,
  ISkill,
  IExperience,
  IJobPost,
} from '../types/userProfileTypes';

export const getUserProfile = createAsyncThunk('user/getProfile', async (userId: number) => {
  const res = await API.get(`api/display/user-profile/${userId}`);
  return res.data;
});

export const editUserProfile = createAsyncThunk(
  'user/editProfile',
  async ({ profile, userId }: { profile: IProfile; userId: number }) => {
    await API.put(`api/user/edit-profile/${userId}`, profile);
    return profile;
  },
);

export const getApplicantProfile = createAsyncThunk(
  'applicant/getProfile',
  async (userId: number) => {
    const res = await API.get(`/api/display/applicant-profile/${userId}`);
    return res.data;
  },
);

export const editApplicantEducation = createAsyncThunk(
  'applicant/editEducation',
  async ({ education, applicantId }: { education: IEducation; applicantId: number }) => {
    await API.put(`/api/applicant/edit-education/${applicantId}`, education);
    return education;
  },
);

export const editApplicantSkillSet = createAsyncThunk(
  'applicant/editSkillSet',
  async ({ skillSet, applicantId }: { skillSet: ISkill[]; applicantId: number }) => {
    await API.put(`/api/applicant/edit-skills/${applicantId}`, skillSet);
    return skillSet;
  },
);

export const editApplicantExperience = createAsyncThunk(
  'applicant/editExperience',
  async ({ experiences, applicantId }: { experiences: IExperience[]; applicantId: number }) => {
    await API.put(`/api/applicant/edit-experiences/${applicantId}`, experiences);
    return experiences;
  },
);

export const getProfileJobPost = createAsyncThunk(
  'sharer/getProfileJobPost',
  async (userId: number) => {
    const res = await API.get(`/api/display/sharer-profile/${userId}/job-post`);
    return res.data;
  },
);

export const createJobPost = createAsyncThunk('sharer/createJobPost', async (job: IJobPost) => {
  const res = await API.put(`/api/sharer/edit-job-post/${job.sharer_id}?function=create`, job);
  return res.data;
});

export const deleteJobPost = createAsyncThunk(
  'sharer/deleteJobPost',
  async ({ userId, jobPostId }: { userId: number; jobPostId?: number }) => {
    await API.put(`/api/sharer/edit-job-post/${userId}?job_post_id=${jobPostId}&function=delete`);
    return jobPostId;
  },
);

export const editJobPost = createAsyncThunk('sharer/editJobPost', async (job: IJobPost) => {
  await API.put(
    `/api/sharer/edit-job-post/${job.sharer_id}?job_post_id=${job.job_post_id}&function=update`,
    job,
  );
  return job;
});

export const getJobPost = createAsyncThunk('sharer/getJobPost', async (jobId: number) => {
  const res = await API.get(`/api/display/job-post/${jobId}`);
  return res.data;
});

const initialState: IProfileState = {
  userProfile: {
    first_name: '',
    last_name: '',
    entry_level: '',
    experience_year: NaN,
    phone_number: '',
    email: '',
  },
  sharerJobPost: [],
};

const profileSlice = createSlice({
  name: 'profile',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getUserProfile.fulfilled, (state, action) => {
      state.userProfile = action.payload;
    });
    builder
      .addCase(editUserProfile.fulfilled, (state, action) => {
        state.userProfile = action.payload;
        toast.success('Edit successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(editUserProfile.rejected, () => {
        toast.error('Edit Failed', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder.addCase(getApplicantProfile.fulfilled, (state, action) => {
      state.education = action.payload.education;
      state.skillSet = action.payload.skills;
      state.experiences = action.payload.experiences;
    });
    builder
      .addCase(editApplicantEducation.fulfilled, (state, action) => {
        state.education = action.payload;
        toast.success('Edit successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(editApplicantEducation.rejected, () => {
        toast.error('Edit Failed', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder
      .addCase(editApplicantSkillSet.fulfilled, (state, action) => {
        state.skillSet = action.payload;
        toast.success('Edit successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(editApplicantSkillSet.rejected, () => {
        toast.error('Edit Failed', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder
      .addCase(editApplicantExperience.fulfilled, (state, action) => {
        state.experiences = action.payload;
        toast.success('Edit successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(editApplicantExperience.rejected, () => {
        toast.error('Edit Failed', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder.addCase(getProfileJobPost.fulfilled, (state, action) => {
      state.sharerJobPost = action.payload;
    });
    builder
      .addCase(createJobPost.fulfilled, (state, action) => {
        state.sharerJobPost?.push(action.payload);
        toast.success('Create New Job successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(createJobPost.rejected, () => {
        toast.error('Create New Job Failed!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder
      .addCase(deleteJobPost.fulfilled, (state, action) => {
        state.sharerJobPost = state.sharerJobPost?.filter(
          (job) => job.job_post_id !== action.payload,
        );
        toast.success('Delete Job successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(deleteJobPost.rejected, () => {
        toast.error('Delete Job Failed!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder
      .addCase(editJobPost.fulfilled, (state, action) => {
        if (state.sharerJobPost) {
          const indexJob = state.sharerJobPost?.findIndex(
            (job) => job.job_post_id === action.payload.job_post_id,
          );
          const job = state.sharerJobPost[indexJob];
          job.job_type = action.payload.job_type;
          job.job_salary = action.payload.job_salary;
          job.company.company_name = action.payload.company.company_name;
          job.company.business_stream = action.payload.company.business_stream;
          job.company.profile_description = action.payload.company.profile_description;
          job.sharer_id = action.payload.sharer_id;
          job.is_available = action.payload.is_available;
          job.job_type = action.payload.job_type;
          job.job_type = action.payload.job_type;
          job.job_description = action.payload.job_description;
          job.job_location = action.payload.job_location;
        }

        toast.success('Edit Job successfully!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(editJobPost.rejected, () => {
        toast.error('Edit Job Failed!!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    builder.addCase(getJobPost.fulfilled, (state, action) => {
      state.sharerJobPost = [action.payload];
    });
  },
});

export const profileReducer = profileSlice.reducer;
