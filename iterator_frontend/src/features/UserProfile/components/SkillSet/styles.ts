import { LinearProgress } from '@material-ui/core';
import { makeStyles, createStyles, Theme, withStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2.5),
      marginTop: theme.spacing(4),
      '& > *': {
        marginBottom: theme.spacing(1.5),
      },
    },
    title: {
      fontWeight: 600,
    },
  }),
);

export const BorderLinearProgress = withStyles((theme: Theme) =>
  createStyles({
    root: {
      height: 5,
      borderRadius: 2.5,
    },
    colorPrimary: {
      backgroundColor: theme.palette.grey[theme.palette.type === 'light' ? 200 : 700],
    },
    bar: {
      borderRadius: 2.5,
      backgroundColor: '#1a90ff',
    },
  }),
)(LinearProgress);
