import {
  Box,
  IconButton,
  ImageList,
  ImageListItem,
  ImageListItemBar,
  Tooltip,
  Typography,
} from '@material-ui/core';
import React from 'react';
import { useStyles } from './styles';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import CancelIcon from '@material-ui/icons/Cancel';
import { EMatchRequestStatus, IRequest, ISetMatchRequestParam } from '../types/requestTypes';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import { getMatchedRequestList, setMatchRequestStatus } from '../store/matchedSlice';
import { useHistory } from 'react-router';

interface IProps {
  title: string;
  requestList: IRequest[];
  showActionIcon?: boolean;
}

export const RequestList = ({ title, requestList, showActionIcon }: IProps) => {
  const classes = useStyles();
  const dispatch = useAppDispatch();
  const { userId } = useAppSelector((state) => state.authLogin);
  const avatar = 'https://bootdey.com/img/Content/avatar/avatar6.png';
  const { applicantId } = useAppSelector((state) => state.authLogin);

  const handleIncomingRequestStatus = async ({
    userId,
    sourceUserId,
    jobPostId,
    body,
  }: ISetMatchRequestParam) => {
    await dispatch(setMatchRequestStatus({ sourceUserId, jobPostId, userId, body }));
    await dispatch(getMatchedRequestList(userId));
  };

  const history = useHistory();

  return (
    <Box mb={5}>
      <Typography variant="h4">{title}</Typography>
      {!!requestList.length ? (
        <div className={classes.root}>
          <ImageList cols={7} rowHeight={120} className={classes.imageList}>
            {requestList.map((item) => (
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
                  title={item.first_name}
                  subtitle={
                    <Tooltip title={item.job_name}>
                      <span>Job: {item.job_name}</span>
                    </Tooltip>
                  }
                  actionIcon={
                    showActionIcon && (
                      <Box display="flex">
                        <IconButton size="small">
                          <CancelIcon
                            className={classes.cancelIcon}
                            onClick={(e) => {
                              e.stopPropagation();
                              handleIncomingRequestStatus({
                                sourceUserId: item.source_user_id,
                                jobPostId: item.job_post_id,
                                userId: userId,
                                body: {
                                  match_status: EMatchRequestStatus.Rejected,
                                },
                              });
                            }}
                          />
                        </IconButton>
                        <IconButton size="small">
                          <CheckCircleIcon
                            className={classes.icon}
                            onClick={(e) => {
                              e.stopPropagation();
                              handleIncomingRequestStatus({
                                sourceUserId: item.source_user_id,
                                jobPostId: item.job_post_id,
                                userId: userId,
                                body: {
                                  match_status: EMatchRequestStatus.Approved,
                                },
                              });
                            }}
                          />
                        </IconButton>
                      </Box>
                    )
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
