import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { CircularProgress } from '@material-ui/core';
import LayoutTemplate from 'templates/LayoutTemplate';
import Login from 'features/Login/Login';
import Register from 'features/Register/Register';
import PrivateRoute from 'navigation/PrivateRoute';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <div className="App">
      <React.Suspense fallback={<CircularProgress />}>
        <BrowserRouter>
          <Switch>
            <Route exact path="/register">
              <Register />
            </Route>
            <Route exact path="/login">
              <Login />
            </Route>
            <PrivateRoute component={LayoutTemplate} path="/"></PrivateRoute>
          </Switch>
        </BrowserRouter>
      </React.Suspense>
      <ToastContainer />
    </div>
  );
}

export default App;
