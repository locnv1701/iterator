import { makeStyles, withStyles, createStyles, Theme } from '@material-ui/core';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import { theme } from 'theme';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      padding: theme.spacing(2.5),
      marginTop: theme.spacing(4),
      '& > *': {
        marginBottom: theme.spacing(2),
      },
    },

    title: {
      fontWeight: 600,
      fontSize: 20,
    },

    jobType: {
      fontWeight: 600,
    },

    activeChip: {
      backgroundColor: theme.palette.success.main,
      color: '#fff',
    },

    chipList: {
      '& > *': {
        marginRight: theme.spacing(1),
      },
    },
  }),
);

export const Accordion = withStyles({
  root: {
    border: '1px solid rgba(0, 0, 0, .125)',
    borderRadius: '5px',
    boxShadow: 'none',

    '&:before': {
      display: 'none',
    },
    '&$expanded': {
      margin: 'auto',
      marginBottom: theme.spacing(2),
    },
  },
  expanded: {},
})(MuiAccordion);

export const AccordionSummary = withStyles({
  root: {
    borderBottom: '1px solid rgba(0, 0, 0, .125)',
    borderRadius: '5px',
    marginBottom: -1,
    minHeight: 56,
    '&$expanded': {
      minHeight: 56,
    },
    '&:hover': {
      backgroundColor: theme.palette.background.default,
    },
  },

  content: {
    '&$expanded': {
      margin: '12px 0',
    },
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  expanded: {},
})(MuiAccordionSummary);

export const AccordionDetails = withStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}))(MuiAccordionDetails);
