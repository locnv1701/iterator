import React, { useEffect, useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import { ThreeDRotation } from '@material-ui/icons';
import logo from '../../assets/images/iterator-logo.png';
import Typography from '@material-ui/core/Typography';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { DataLogin } from './typeLogin';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { loginAuth } from 'redux/authLogin/loginSlice';
import { RootState } from 'store/store';
import { resetIsFulfilled } from 'redux/Register/sliceRegister';

const schema = yup
  .object()
  .shape({
    user_name: yup.string().required('User Name is required'),
    user_password: yup
      .string()
      .min(8, 'Password must be at least 8 characters')
      .max(20)
      .required('Password is required'),
  })
  .required();
function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles((theme: Theme) => ({
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
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function Login() {
  const classes = useStyles();
  const [remember, setRemember] = useState(false);
  const token = useSelector((state: RootState) => state.authLogin.token);
  const history = useHistory();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<DataLogin>({
    resolver: yupResolver(schema),
  });

  const dispatch = useDispatch();
  function handleOnSubmitForm(data: DataLogin) {
    dispatch(loginAuth(data));
  }

  useEffect(() => {
    dispatch(resetIsFulfilled());
  }, [dispatch]);

  useEffect(() => {
    if (token) {
      history.push('/');
    }
  }, [token, history]);

  function handleRememberAccount(e: React.ChangeEvent<HTMLInputElement>) {
    setRemember(e.target.checked);
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <ThreeDRotation />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <form onSubmit={handleSubmit(handleOnSubmitForm)} className={classes.form}>
          <TextField
            {...register('user_name')}
            error={!!errors.user_name}
            helperText={errors.user_name ? errors.user_name?.message : ''}
            variant="outlined"
            margin="normal"
            fullWidth
            label="User Name"
            name="user_name"

            // autoFocus
          />
          <TextField
            {...register('user_password')}
            error={!!errors.user_password}
            helperText={errors.user_password ? errors.user_password?.message : ''}
            variant="outlined"
            margin="normal"
            fullWidth
            name="user_password"
            label="Password"
            type="password"
          />
          <FormControlLabel
            control={<Checkbox value="remember" color="primary" onChange={handleRememberAccount} />}
            label="Remember me"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign In
          </Button>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid>
            <Grid item>
              <Link href="/register" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      <Box mt={8}>
        <Copyright />
      </Box>
    </Container>
  );
}
