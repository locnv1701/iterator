import { Box, makeStyles, TextField, createStyles, IconButton } from '@material-ui/core';
import React from 'react';
import { Control, Controller, useFieldArray } from 'react-hook-form';
import { ISkillSet } from '../SkillSet';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import DeleteIcon from '@material-ui/icons/Delete';

interface Props {
  control: Control<ISkillSet, object>;
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
  }),
);

export const FieldArray = ({ control }: Props) => {
  const classes = useStyles();
  const { fields, append, remove } = useFieldArray({
    control,
    name: 'skills',
  });

  return (
    <>
      {fields.map((item, index) => {
        return (
          <Box key={index} className={classes.skillEdit}>
            {/*@ts-ignore*/}
            <Controller
              name={`skills.${index}.skill_name` as const}
              control={control}
              render={({ field }) => <TextField {...field} style={{ marginRight: 30 }} />}
            />
            <Controller
              name={`skills.${index}.level` as const}
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  type="number"
                  InputProps={{ inputProps: { min: 0, max: 10, step: 1 } }}
                />
              )}
            />
            <IconButton>
              <DeleteIcon color="error" onClick={() => remove(index)} />
            </IconButton>
          </Box>
        );
      })}
      <Box>
        <AddCircleOutlineIcon
          onClick={() => {
            append({ skill_name: '', level: 0 });
          }}
          className={classes.addIcon}
        />
      </Box>
    </>
  );
};
