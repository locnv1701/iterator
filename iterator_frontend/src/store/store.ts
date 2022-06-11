import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import homeReducer from '../features/home/store/homeSlice';
import authLogin from 'redux/authLogin/loginSlice';
import registerReducer from 'redux/Register/sliceRegister';
import { profileReducer } from 'features/UserProfile/store/userProfileSlices';
import { matchedReducer } from 'features/Matched/store/matchedSlice';
export const store = configureStore({
  reducer: {
    home: homeReducer,
    authLogin: authLogin,
    register: registerReducer,
    profile: profileReducer,
    matched: matchedReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
