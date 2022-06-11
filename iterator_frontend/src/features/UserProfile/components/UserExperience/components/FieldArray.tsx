import {
  Box,
  makeStyles,
  TextField,
  createStyles,
  IconButton,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  TextareaAutosize,
} from '@material-ui/core';
import React from 'react';
import { Control, Controller, useFieldArray } from 'react-hook-form';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import DeleteIcon from '@material-ui/icons/Delete';
import { IExperiences } from '../UserExperience';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

interface Props {
  control: Control<IExperiences, object>;
}

export const useStyles = makeStyles(() =>
  createStyles({
    skillEdit: {
      display: 'flex',
      justifyContent: 'space-between',
      '& > *': {
        marginBottom: 16,
      },
    },
    addIcon: {
      cursor: 'pointer',
      color: '#17A2B8',
    },
    column: {
      width: '30%',
      marginRight: 15,
    },
    box: {
      display: 'flex',
      alignItems: 'center',
    },
  }),
);

export const FieldArray = ({ control }: Props) => {
  const classes = useStyles();
  const { fields, append, remove } = useFieldArray({
    control,
    name: 'experiences',
  });

  return (
    <>
      {/*@ts-ignore*/}
      <Box ml={2} mb={2}>
        <AddCircleOutlineIcon
          onClick={() => {
            append({
              job_title: '',
              company_name: '',
              start_date: '',
              end_date: '',
              project_self_description: '',
            });
          }}
          className={classes.addIcon}
        />
      </Box>
      {fields.map((item, index) => {
        return (
          <Accordion key={index}>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <div className={classes.column}>
                <Controller
                  name={`experiences.${index}.job_title` as const}
                  control={control}
                  render={({ field }) => <TextField label="Position" {...field} />}
                />
              </div>
              <div className={classes.column}>
                <Controller
                  name={`experiences.${index}.company_name` as const}
                  control={control}
                  render={({ field }) => <TextField label="Company" {...field} />}
                />
              </div>
              <div className={classes.column}>
                <Box>
                  <Controller
                    name={`experiences.${index}.start_date` as const}
                    control={control}
                    render={({ field }) => (
                      <TextField
                        InputLabelProps={{
                          shrink: true,
                        }}
                        type="date"
                        label="Start at"
                        style={{ marginBottom: 10 }}
                        {...field}
                      />
                    )}
                  />
                  <Controller
                    name={`experiences.${index}.end_date` as const}
                    control={control}
                    render={({ field }) => (
                      <TextField
                        InputLabelProps={{
                          shrink: true,
                        }}
                        type="date"
                        label="End at"
                        {...field}
                      />
                    )}
                  />
                </Box>
              </div>
              <Box className={classes.box}>
                <IconButton>
                  <DeleteIcon onClick={() => remove(index)} color="error" />
                </IconButton>
              </Box>
            </AccordionSummary>
            <AccordionDetails>
              {/*@ts-ignore*/}
              <Controller
                name={`experiences.${index}.project_self_description` as const}
                control={control}
                render={({ field }) => (
                  <TextareaAutosize
                    {...field}
                    style={{ width: '100%' }}
                    aria-label="minimum height"
                    minRows={3}
                  />
                )}
              />
            </AccordionDetails>
          </Accordion>
        );
      })}
    </>
  );
};
