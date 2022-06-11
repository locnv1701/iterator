import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import API from 'api/api';
import { IMatched, IRequest, ISetMatchRequestParam } from '../types/requestTypes';

interface MatchedState {
  incomingRequest: IRequest[];
  matchedRequest: IMatched[];
}

export const getIncomingRequestList = createAsyncThunk(
  'user/getIncomingRequestList',
  async (userId: number) => {
    const res = await API.get(`api/display/incoming-request/${userId}`);
    return res.data;
  },
);

export const setMatchRequestStatus = createAsyncThunk(
  'user/setMacthRequestStatus',
  async ({ userId, sourceUserId, jobPostId, body }: ISetMatchRequestParam) => {
    const res = await API.put(
      `api/incoming-request/${userId}?source_user_id=${sourceUserId}&job_post_id=${jobPostId}`,
      body,
    );
    return { sourceUserId: sourceUserId, jobPostId: jobPostId, status: body.match_status };
  },
);

export const getMatchedRequestList = createAsyncThunk(
  'user/getMatchedRequestList',
  async (userId: number) => {
    const res = await API.get(`api/display/match-request/${userId}?page=0&size=10`);
    return res.data;
  },
);

const initialState: MatchedState = {
  incomingRequest: [],
  matchedRequest: [],
};

export const matchedSlice = createSlice({
  name: 'matched',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getIncomingRequestList.fulfilled, (state, action) => {
      state.incomingRequest = action.payload;
    });
    builder.addCase(setMatchRequestStatus.fulfilled, (state, { payload }) => {
      state.incomingRequest = state.incomingRequest.filter(
        ({ job_post_id, source_user_id }) =>
          source_user_id !== payload.sourceUserId || job_post_id !== payload.jobPostId,
      );
    });
    builder.addCase(getMatchedRequestList.fulfilled, (state, action) => {
      state.matchedRequest = action.payload;
    });
  },
});

export const {} = matchedSlice.actions;

export const matchedReducer = matchedSlice.reducer;
