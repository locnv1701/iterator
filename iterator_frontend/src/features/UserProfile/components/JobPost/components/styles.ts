import { makeStyles } from '@material-ui/core';
import { createStyles } from '@material-ui/styles';

export const useStyles = makeStyles(() =>
  createStyles({
    rowWithTwoCols: {
      display: 'flex',
      justifyContent: 'space-between',
    },
    checkboxControl: {
      position: 'relative',
      left: 0,
    },
  }),
);
