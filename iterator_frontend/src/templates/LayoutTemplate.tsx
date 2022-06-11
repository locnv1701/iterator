import { Box, Container, Toolbar } from '@material-ui/core';
import PrimarySearchAppBar from 'components/AppBar/AppBar';
import { Blogs } from 'features/Blogs/Blogs';
import HomePage from 'features/home/HomePage';
import { Matched } from 'features/Matched/Matched';
import { MatchedProfile } from 'features/MatchedProfile/MatchedApplicantProfile';
import { MatchedSharerProfile } from 'features/MatchedProfile/MatchedSharerProfile';
import { getProfileJobPost } from 'features/UserProfile/store/userProfileSlices';
import { UserProfile } from 'features/UserProfile/UserProfile';
import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Route } from 'react-router';
import { useAppSelector } from 'store/hooks';

const LayoutTemplate = () => {
  const dispatch = useDispatch();
  const { userId, applicantId } = useAppSelector((state) => state.authLogin);

  useEffect(() => {
    !applicantId && dispatch(getProfileJobPost(userId));
  }, [dispatch, userId, applicantId]);

  return (
    <>
      <PrimarySearchAppBar />
      <Box height="100vh" display="flex" flexDirection="column">
        <Toolbar />
        <Route exact path="/">
          <HomePage />
        </Route>
        <Route path="/blogs">
          <Container>
            <Box mx={10} my={3}>
              <Blogs />
            </Box>
          </Container>
        </Route>
        <Route path="/matched">
          <Container>
            <Box mx={10} my={3}>
              <Matched />
            </Box>
          </Container>
        </Route>
        <Route exact path="/profile">
          <Container>
            <Box mx={10} my={3}>
              <UserProfile />
            </Box>
          </Container>
        </Route>
        <Route path="/profile/applicant/:id">
          <Container>
            <Box mx={10} my={3}>
              <MatchedProfile />
            </Box>
          </Container>
        </Route>
        <Route path="/profile/sharer/:id/:jobId">
          <Container>
            <Box mx={10} my={3}>
              <MatchedSharerProfile />
            </Box>
          </Container>
        </Route>
      </Box>
    </>
  );
};

export default LayoutTemplate;
