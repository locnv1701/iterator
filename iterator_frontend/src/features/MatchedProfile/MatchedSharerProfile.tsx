import { Grid } from '@material-ui/core';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { RootState } from 'store/store';

import React, { useEffect } from 'react';
import FacebookIcon from '@material-ui/icons/Facebook';
import GitHubIcon from '@material-ui/icons/GitHub';
import TwitterIcon from '@material-ui/icons/Twitter';
import InstagramIcon from '@material-ui/icons/Instagram';
import { theme } from 'theme';

import { useParams } from 'react-router';
import {
  getApplicantProfile,
  getJobPost,
  getProfileJobPost,
  getUserProfile,
} from 'features/UserProfile/store/userProfileSlices';
import JobPost from 'features/UserProfile/components/JobPost/JobPost';
import { UserInfo } from 'features/UserProfile/components/UserInfo/UserInfo';
import { SocialMedia } from 'features/UserProfile/components/SocialMedia/SocialMedia';

type IdParams = {
  id: string | undefined;
  jobId: string | undefined;
};

export const MatchedSharerProfile = () => {
  const { userProfile, sharerJobPost } = useAppSelector((state: RootState) => state.profile);

  const { id, jobId } = useParams<IdParams>();

  const dispatch = useAppDispatch();

  const userInfo = [
    {
      label: 'First Name',
      value: userProfile.first_name,
    },
    {
      label: 'Last Name',
      value: userProfile.last_name,
    },
    {
      label: 'Email',
      value: userProfile.email,
    },
    {
      label: 'Phone',
      value: userProfile.phone_number,
    },
    {
      label: 'Location',
      value: userProfile.location || '',
    },
    {
      label: 'Position',
      value: userProfile.entry_level,
    },
  ];

  const socialMediaList = [
    {
      icon: <GitHubIcon />,
      name: 'Github',
      link: userProfile.github_link || '',
    },
    {
      icon: <TwitterIcon style={{ color: `${theme.palette.info.light}` }} />,
      name: 'Twitter',
      link: userProfile.twitter_link || '',
    },
    {
      icon: <InstagramIcon color="secondary" />,
      name: 'Instagram',
      link: userProfile.instagram_link || '',
    },
    {
      icon: <FacebookIcon color="primary" />,
      name: 'Facebook',
      link: userProfile.facebook_link || '',
    },
  ];

  useEffect(() => {
    dispatch(getUserProfile(Number(id)));
    dispatch(getApplicantProfile(Number(id)));
    dispatch(getJobPost(Number(jobId)));
  }, [dispatch, id, jobId]);

  return (
    <Grid container spacing={4}>
      <Grid item xs={4}>
        <Grid item xs={12}>
          <SocialMedia
            firstName={userProfile.first_name}
            lastName={userProfile.last_name}
            position={userProfile.entry_level}
            socialMediaList={socialMediaList}
            userId={Number(id)}
            hideEditButton
          />
        </Grid>
      </Grid>

      <Grid item xs={8}>
        <Grid item xs={12}>
          <UserInfo userId={Number(id)} content={userInfo} hideEditButton />
        </Grid>

        <Grid item xs={12}>
          <JobPost jobsPost={sharerJobPost} userId={Number(id)} hideEditButton />
        </Grid>
      </Grid>
    </Grid>
  );
};
