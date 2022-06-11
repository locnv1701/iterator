import { Button } from '@material-ui/core';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';
import { styled } from '@material-ui/styles';
import { theme } from 'theme';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2),
      textAlign: 'center',
    },
  }),
);

export const EditButton = styled(Button)({
  background: '#17A2B8',
  color: theme.palette.primary.contrastText,
  textTransform: 'unset',
  marginTop: theme.spacing(1),
});

export const CancelButton = styled(Button)({
  textTransform: 'unset',
  marginTop: theme.spacing(1),
  marginRight: theme.spacing(2),
});
