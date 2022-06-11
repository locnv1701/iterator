import {
  Box,
  ImageList,
  ImageListItem,
  ImageListItemBar,
  Tooltip,
  Typography,
} from '@material-ui/core';
import React from 'react';
import { useHistory } from 'react-router';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { IMatched } from '../types/requestTypes';
import { useStyles } from './styles';

interface IProps {
  title: string;
  matchedList: IMatched[];
}

export const MatchedList = ({ title, matchedList }: IProps) => {
  const classes = useStyles();
  const history = useHistory();
  const { applicantId } = useAppSelector((state) => state.authLogin);
  const avatar = 'https://bootdey.com/img/Content/avatar/avatar6.png';

  return (
    <Box mb={5}>
      <Typography variant="h4">{title}</Typography>
      {!!matchedList.length ? (
        <div className={classes.root}>
          <ImageList cols={7} rowHeight={120} className={classes.imageList}>
            {matchedList.map((item) => (
              <ImageListItem
                onClick={() =>
                  history.push(
                    `/profile/${!!applicantId ? 'sharer' : 'applicant'}/${item.source_user_id}/${
                      item.job_post_id
                    }`,
                  )
                }
                className={classes.item}
                key={`${item.job_post_id}-${item.source_user_id}`}
              >
                <img className={classes.img} src={avatar} alt="" />
                <ImageListItemBar
                  title={`${item.first_name} ${item.last_name}`}
                  subtitle={
                    <Tooltip title={item.job_name}>
                      <span>Job: {item.job_name}</span>
                    </Tooltip>
                  }
                />
              </ImageListItem>
            ))}
          </ImageList>
        </div>
      ) : (
        <Box textAlign="center">
          <Typography variant="body2">Empty Request</Typography>
        </Box>
      )}
    </Box>
  );
};
