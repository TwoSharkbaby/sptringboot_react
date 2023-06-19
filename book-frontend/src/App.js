import React from 'react';
import { Route } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/book/Home';
import SaveForm from './pages/book/SaveForm';
import Detail from './pages/book/Detail';
import Login from './pages/user/Login';
import JoinForm from './pages/user/JoinForm';
import UpdateForm from './pages/book/UpdateForm';

function App() {
  return (
    <div>
      <Header />
      <Container>
        <Route path="/" exact={true} component={Home} />
        <Route path="/saveForm" exact={true} component={SaveForm} />
        <Route path="/book/:id" exact={true} component={Detail} />
        <Route path="/loginForm" exact={true} component={Login} />
        <Route path="/joinForm" exact={true} component={JoinForm} />
        <Route path="/updateForm/:id" exact={true} component={UpdateForm} />
      </Container>
      <Footer />
    </div>
  );
}

export default App;
