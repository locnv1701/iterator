import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import API from 'api/api';
import { DataLogin } from 'features/Login/typeLogin';
import { toast } from 'react-toastify';
import {
  getAccessToken,
  getApplicantId,
  getUserId,
  removeAccessToken,
  removeApplicantId,
  removeUserId,
  setAccessToken,
  setApplicantId,
  setUserId,
} from 'utils/localStorage';
import { IAuthState } from './types';

export const loginAuth = createAsyncThunk('auth/loginAuth', async (dataLogin: DataLogin) => {
  const response = await API.post('api/user/login', dataLogin);
  return response.data;
});

const initialState: IAuthState = {
  token: getAccessToken(),
  userId: Number(getUserId()),
  applicantId: Number(getApplicantId()),
};

const loginSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logoutSuccess(state) {
      state.token = null;
      removeUserId();
      removeAccessToken();
      removeApplicantId();
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginAuth.fulfilled, (state, action) => {
        state.token = action.payload.login_token;
        state.userId = action.payload.user_id;
        state.applicantId = action.payload.applicant_id;
        setAccessToken(action.payload.login_token);
        setUserId(action.payload.user_id);
        setApplicantId(action.payload.applicant_id);
      })
      .addCase(loginAuth.rejected, () => {
        toast.error('Login Failed', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
  },
});

const { reducer, actions } = loginSlice;
export const { logoutSuccess } = actions;
export default reducer;
