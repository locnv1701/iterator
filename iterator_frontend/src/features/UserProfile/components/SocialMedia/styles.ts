import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2.5),
    },
    content: {
      textAlign: 'center',
      '& > *': {
        marginBottom: theme.spacing(1),
      },
    },
    box: {
      display: 'flex',
      justifyContent: 'center',
    },
    socialMedia: {
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
    },
    socialMediaLeft: {
      display: 'flex',
      alignItems: 'center',
    },
    userName: {
      fontWeight: 550,
      fontSize: `1.5rem`,
      lineHeight: 1.2,
    },
    large: {
      width: theme.spacing(13),
      height: theme.spacing(13),
      border: `4px solid ${theme.palette.primary.main}`,
    },
    deleteIcon: {
      position: 'absolute',
      top: -10,
      right: 0,
      cursor: 'pointer',
    },
  }),
);
