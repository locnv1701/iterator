import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      width: '100%',
    },
    heading: {
      fontSize: theme.typography.pxToRem(15),
      fontWeight: theme.typography.fontWeightRegular,
    },
    paper: {
      '& > *': {
        marginBottom: theme.spacing(2),
      },
      marginTop: theme.spacing(4),
      marginBottom: theme.spacing(4),
    },
    title: {
      padding: '10px 16px',
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      '& > p': {
        fontWeight: 600,
      },
      flexWrap: 'wrap',
    },
    column: {
      width: '33.33%',
    },
    time: {
      display: 'flex',
      justifyContent: 'space-between',
    },
  }),
);
