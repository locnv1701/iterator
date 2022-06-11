import React, { useState, Fragment } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { NavLink } from 'react-router-dom';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import { useForm } from 'react-hook-form';
import { DataFormRegister } from './typeRegister';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useDispatch } from 'react-redux';
import { registerApi } from 'redux/Register/sliceRegister';
import { useAppSelector } from 'store/hooks';
import { RootState } from 'store/store';
import { useHistory } from 'react-router-dom';
import API from 'api/api';

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(3),
    paddingBottom: theme.spacing(4),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

const schema = yup
  .object()
  .shape({
    first_name: yup.string().required('Please enter first name'),
    last_name: yup.string().required('Please enter last name'),
    email: yup.string().email('Email is invalid').required('Please enter email'),
    user_name: yup.string().required('User Name is required'),
    user_password: yup
      .string()
      .min(8, 'Password must be at least 8 characters')
      .max(20)
      .required('Password is required'),
    user_type_id: yup.string().required(),
    phone_number: yup.string().required('Phone number is required'),
  })
  .required();

export const positionList = [
  'Help desk technician',
  'IT technician',
  'Web developer',
  'Systems administrator',
  'Systems analyst',
  'Database administrator',
  'Site reliability engineer',
  'Software developer',
];

export default function Register() {
  const classes = useStyles();
  const [isCandidate, setIsCandidate] = useState(2);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<DataFormRegister>({
    resolver: yupResolver(schema),
  });
  const dispatch = useDispatch();
  const isSuccess = useAppSelector((state: RootState) => state.register.isFulfilled);
  const history = useHistory();
  if (isSuccess) {
    history.push('/login');
  }

  const handleSubmitRegister = (data: DataFormRegister) => {
    dispatch(registerApi({ ...data, location: '' }));
  };

  const handleChange = () => {
    if (isCandidate === 1) {
      setIsCandidate(2);
    } else {
      setIsCandidate(1);
    }
  };
  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form} onSubmit={handleSubmit(handleSubmitRegister)}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <FormControl variant="outlined" fullWidth>
                <InputLabel id="demo-simple-select-outlined-label">User Type</InputLabel>
                <Select
                  {...register('user_type_id')}
                  value={isCandidate}
                  onChange={handleChange}
                  label="User Type"
                  name="user_type_id"
                >
                  <MenuItem value={'2'}>Recruiter</MenuItem>
                  <MenuItem value={'1'}>Job Seeker</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                {...register('first_name')}
                error={!!errors.first_name}
                helperText={errors.first_name ? errors.first_name?.message : ''}
                name="first_name"
                variant="outlined"
                fullWidth
                label="First Name"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                {...register('last_name')}
                error={!!errors.last_name}
                helperText={errors.last_name ? errors.last_name?.message : ''}
                variant="outlined"
                fullWidth
                label="Last Name"
                name="last_name"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                {...register('email')}
                error={!!errors.email}
                helperText={errors.email ? errors.email?.message : ''}
                variant="outlined"
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                {...register('user_name')}
                error={!!errors.user_name}
                helperText={errors.user_name ? errors.user_name?.message : ''}
                variant="outlined"
                fullWidth
                label="User Name"
                name="user_name"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                {...register('user_password')}
                error={!!errors.user_password}
                helperText={errors.user_password ? errors.user_password?.message : ''}
                variant="outlined"
                fullWidth
                name="user_password"
                label="Password"
                type="password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                {...register('phone_number')}
                error={!!errors.phone_number}
                helperText={errors.phone_number ? errors.phone_number?.message : ''}
                variant="outlined"
                fullWidth
                label="Phone Number"
                name="phone_number"
              />
            </Grid>
            {isCandidate === 1 && (
              <Fragment>
                <Grid item xs={12}>
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel>Entry Level</InputLabel>
                    <Select
                      {...register('entry_level')}
                      labelId="demo-simple-select-outlined-label"
                      id="demo-simple-select-outlined"
                      //   value={age}
                      //   onChange={handleChange}
                      label="Entry Level"
                      name="entry_level"
                    >
                      <MenuItem value="">
                        <em>None</em>
                      </MenuItem>
                      {positionList.map((position, index) => (
                        <MenuItem key={index} value={position}>
                          {position}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    {...register('experience_year')}
                    error={!!errors.experience_year}
                    helperText={errors.experience_year ? errors.experience_year?.message : ''}
                    variant="outlined"
                    fullWidth
                    id="experience"
                    label="Experience Year"
                    name="experience_year"
                    autoComplete="experience"
                  />
                </Grid>
              </Fragment>
            )}
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign Up
          </Button>
          <Grid container justifyContent="flex-end">
            <Grid item>
              <NavLink to="/login">Already have an account? Sign in</NavLink>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
}
