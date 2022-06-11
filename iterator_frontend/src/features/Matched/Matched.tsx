import React, { useEffect } from 'react';

import { Box } from '@material-ui/core';
import { RequestList } from './components/RequestList';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { getIncomingRequestList, getMatchedRequestList } from './store/matchedSlice';
import { MatchedList } from './components/MatchedList';

export const Matched = () => {
  const dispatch = useAppDispatch();
  const { userId } = useAppSelector((state) => state.authLogin);
  const { incomingRequest, matchedRequest } = useAppSelector((state) => state.matched);

  useEffect(() => {
    dispatch(getIncomingRequestList(userId));
    dispatch(getMatchedRequestList(userId));
  }, [dispatch, userId]);

  return (
    <Box height="100%">
      <RequestList title="Incoming Request" requestList={incomingRequest} showActionIcon />
      <MatchedList title="Matched Request" matchedList={matchedRequest} />
    </Box>
  );
};
