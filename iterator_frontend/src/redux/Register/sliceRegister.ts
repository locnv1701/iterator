import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import API from 'api/api';
import { DataFormRegister } from 'features/Register/typeRegister';
import { toast } from 'react-toastify';

export const registerApi = createAsyncThunk(
  'register/registerApi',
  async (infoUser: DataFormRegister) => {
    const response = await API.post('api/user/register', infoUser);
    return response.data;
  },
);

interface StateToken {
  loading: boolean;
  isFulfilled: boolean;
}

const initialState: StateToken = {
  loading: false,
  isFulfilled: false,
};

const registerSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    resetIsFulfilled: (state) => {
      state.isFulfilled = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerApi.pending, (state) => {
        state.loading = true;
      })
      .addCase(registerApi.fulfilled, (state) => {
        state.loading = false;
        state.isFulfilled = true;
        toast.success('Register Successfully !!!', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .addCase(registerApi.rejected, (state, action) => {
        state.loading = false;

        toast.error(`Failed`, {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
  },
});

export const { resetIsFulfilled } = registerSlice.actions;

const { reducer } = registerSlice;
export default reducer;
