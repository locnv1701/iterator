import { Grid } from '@material-ui/core';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { RootState } from 'store/store';
import { SkillSet } from './components/SkillSet/SkillSet';
import { SocialMedia } from './components/SocialMedia/SocialMedia';
import { UserExperience } from './components/UserExperience/UserExperience';
import React, { useEffect } from 'react';
import FacebookIcon from '@material-ui/icons/Facebook';
import GitHubIcon from '@material-ui/icons/GitHub';
import TwitterIcon from '@material-ui/icons/Twitter';
import InstagramIcon from '@material-ui/icons/Instagram';
import { theme } from 'theme';
import { getApplicantProfile, getUserProfile } from './store/userProfileSlices';
import { UserInfo } from './components/UserInfo/UserInfo';
import { ApplicantEducation } from './components/ApplicantEducation/ApplicantEducation';
import JobPost from './components/JobPost/JobPost';
import { useParams } from 'react-router';

type IdParams = {
  id: string | undefined;
};

/* <Link to={`/app/projects/${project.id}`}></Link> */
export const UserProfile = () => {
  const { userProfile, education, skillSet, experiences, sharerJobPost } = useAppSelector(
    (state: RootState) => state.profile,
  );
  const { userId, applicantId } = useAppSelector((state) => state.authLogin);

  const { id } = useParams<IdParams>();

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

  const educationList = [
    {
      label: 'University',
      value: education?.college_name,
    },
    {
      label: 'Major',
      value: education?.major,
    },
    {
      label: 'GPA',
      value: education?.cgpa,
    },
    {
      label: 'Starting date',
      value: education?.starting_date,
    },
    {
      label: 'Completion date',
      value: education?.completion_date,
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
    dispatch(getUserProfile(userId));
    !!applicantId && dispatch(getApplicantProfile(userId));
  }, [dispatch, userId, applicantId]);

  return (
    <Grid container spacing={4}>
      <Grid item xs={4}>
        <Grid item xs={12}>
          <SocialMedia
            firstName={userProfile.first_name}
            lastName={userProfile.last_name}
            position={userProfile.entry_level}
            socialMediaList={socialMediaList}
            userId={userId}
          />
        </Grid>
        {!isNaN(applicantId) && (
          <Grid item xs={12}>
            <SkillSet applicantId={applicantId} skillSet={skillSet} />
          </Grid>
        )}
      </Grid>
      <Grid item xs={8}>
        <Grid item xs={12}>
          <UserInfo userId={userId} content={userInfo} />
        </Grid>
        {!isNaN(applicantId) && (
          <Grid item xs={12}>
            <UserExperience applicantId={applicantId} experiences={experiences} />
          </Grid>
        )}
        {!isNaN(applicantId) && (
          <Grid item xs={12}>
            <ApplicantEducation
              applicantId={applicantId}
              title="Education"
              content={educationList}
            />
          </Grid>
        )}
        {sharerJobPost && !applicantId && (
          <Grid item xs={12}>
            <JobPost jobsPost={sharerJobPost} userId={userId} />
          </Grid>
        )}
      </Grid>
    </Grid>
  );
};
