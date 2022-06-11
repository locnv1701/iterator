import { Avatar, Box, Button, Paper, Typography, Divider, Link } from '@material-ui/core';
import React, { useState } from 'react';
import { useStyles } from './styles';
import { CancelButton, EditButton } from 'features/UserProfile/styles';

import { SocialMediaForm } from './components/SocialMediaForm';
import { IProfile, ISocialMedia } from 'features/UserProfile/types/userProfileTypes';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { editUserProfile } from 'features/UserProfile/store/userProfileSlices';
import { RootState } from 'store/store';
import { useForm } from 'react-hook-form';

interface Props {
  firstName: string;
  lastName: string;
  position: string;
  socialMediaList: {
    name: string;
    link: string;
    icon: JSX.Element;
  }[];
  userId: number;
  hideEditButton?: boolean;
}

export const SocialMedia = ({
  firstName,
  lastName,
  position,
  socialMediaList,
  userId,
  hideEditButton,
}: Props) => {
  const classes = useStyles();
  const dispatch = useAppDispatch();
  const userProfile = useAppSelector((state: RootState) => state.profile.userProfile);
  const defaultValues = {
    github: userProfile.github_link,
    twitter: userProfile.twitter_link,
    instagram: userProfile.instagram_link,
    facebook: userProfile.facebook_link,
  };
  const { handleSubmit, control, reset } = useForm<ISocialMedia>({ defaultValues: defaultValues });
  const clickEditButton = () => {
    reset(defaultValues);
    setIsEditing(true);
  };
  const [isEditing, setIsEditing] = useState(false);
  const onEditSocialMedia = (data: ISocialMedia) => {
    const newUserProfile: IProfile = {
      ...userProfile,
      facebook_link: data.facebook,
      instagram_link: data.instagram,
      github_link: data.github,
      twitter_link: data.twitter,
      profile_display_name: 'Chí Công',
      age: 22,
      gender: 'MALE',
      location: 'Ha Noi',
      status: 'ACTIVE',
    };
    dispatch(
      editUserProfile({
        profile: newUserProfile,
        userId,
      }),
    );
    setIsEditing(false);
  };

  return (
    <Paper elevation={3} className={classes.paper}>
      <form id="socialMedia" onSubmit={handleSubmit(onEditSocialMedia)}>
        <Box className={classes.content}>
          <Box className={classes.box}>
            <Avatar
              alt="avt-user"
              src="https://bootdey.com/img/Content/avatar/avatar6.png"
              className={classes.large}
            />
          </Box>
          <Typography className={classes.userName}>{`${lastName} ${firstName}`}</Typography>
          <Typography color="textSecondary">{position}</Typography>
          <Box>
            <Button variant="contained" color="primary" style={{ marginRight: '10px' }}>
              Follow
            </Button>
            <Button variant="outlined" color="primary">
              Message
            </Button>
          </Box>
          <Box my={3}>
            <Divider />
          </Box>
          {!isEditing ? (
            socialMediaList.map(
              (socialMedia, index) =>
                socialMedia.link !== '' && (
                  <Link key={index} href={socialMedia.link} variant="body2">
                    <Box color="primary" key={index} className={classes.socialMedia} pb={1}>
                      <Typography component={'div'} className={classes.socialMediaLeft}>
                        {socialMedia.icon}
                        <Box ml={1}>{socialMedia.name}</Box>
                      </Typography>
                    </Box>
                  </Link>
                ),
            )
          ) : (
            <SocialMediaForm control={control} />
          )}
        </Box>
        {isEditing ? (
          <>
            <CancelButton onClick={() => setIsEditing(false)} variant="contained">
              Cancel
            </CancelButton>
            <EditButton type="submit" form="socialMedia" variant="contained">
              Confirm
            </EditButton>
          </>
        ) : (
          !hideEditButton && (
            <EditButton onClick={clickEditButton} variant="contained">
              Edit
            </EditButton>
          )
        )}
      </form>
    </Paper>
  );
};
