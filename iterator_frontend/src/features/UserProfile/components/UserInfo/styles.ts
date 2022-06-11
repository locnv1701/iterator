import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2.5),
      '& > *': {
        marginBottom: theme.spacing(2),
      },
    },

    label: {
      fontWeight: 600,
      justifyContent: 'flex-start',
    },

    title: {
      fontWeight: 600,
    },
  }),
);
