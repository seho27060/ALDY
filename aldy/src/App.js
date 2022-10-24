import './App.css';
import { Routes, Route } from 'react-router-dom'
import MainPage from './pages/MainPage'

function App() {
  return (
    <div className="App">
      <Routes>
        <Route element={<MainPage />} path="/main"></Route>
      </Routes>
    </div>
  );
}

export default App;
