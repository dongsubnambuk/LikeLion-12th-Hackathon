import React from 'react';
import'./App.css';
import BottomNav from './components/BottomNav';
import Header from'./components/Header';
import MainPage from './pages/MainPage';

const App = () => (
  <div className="mobile-container">
  
  <Header/>
  <BottomNav/>
  <MainPage/>

</div>
);

export default App;
