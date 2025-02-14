import './App.css';
import Login from './components/Login';
import SignUp from './components/SignUp';
import ForgetPassword from './components/ForgetPassword';
import Dashboard from './components/Dashboard';
import Author from './components/Author';
import Category from './components/Category';
import ResetPassword from './components/ResetPassword';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Book from './components/Book';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/forgot" element={<ForgetPassword />} />
        <Route path="/dashboard" element={<Dashboard />} /> 
        <Route path="/reset-password" element={<ResetPassword />} />
        {/* <Route path="" element={<ForgetPassword />} /> */}
        <Route path="/category" element={<Category />} />
        <Route path="/authors" element={<Author />} />
        <Route path="/book" element={<Book />} />
      </Routes>
    </Router>
  );
}

export default App;
