import { Button } from '@material-ui/core';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';
import { styled } from '@material-ui/styles';
import { theme } from 'theme';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    filterWrapper: {
      backgroundColor: theme.palette.background.default,
      '& > *': {
        marginBottom: theme.spacing(2),
      },
    },
  }),
);
