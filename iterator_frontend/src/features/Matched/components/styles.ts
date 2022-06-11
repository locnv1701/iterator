import { makeStyles, createStyles, Theme } from '@material-ui/core';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
      flexWrap: 'wrap',
      justifyContent: 'space-around',
      overflow: 'hidden',
      backgroundColor: theme.palette.background.paper,
      marginTop: theme.spacing(1),
    },
    imageList: {
      width: '100%',
      height: '100%',
    },
    img: {
      objectFit: 'contain',
    },
    icon: {
      color: theme.palette.success.main,
    },
    cancelIcon: {
      color: theme.palette.error.main,
      marginRight: theme.spacing(0.5),
    },
    item: {
      cursor: 'pointer',
    },
  }),
);
