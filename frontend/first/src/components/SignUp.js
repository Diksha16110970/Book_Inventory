import React, { useState } from 'react';
import './Login.css';
import {toast, ToastContainer} from "react-toastify"
import "react-toastify/dist/ReactToastify.css"

function SignUp() {
  const [register, setRegister] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [passwordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const handleChange = (e) => {
    setRegister({
      ...register,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (register.password !== register.confirmPassword) {
      //alert("Passwords do not match!");
      toast.error("Passwords do not match!", {position:"top-right", autoClose:3000})
      return;
    }
    if (register.password.length < 6) {
      toast.error("Password must be at least 6 characters long", {
        position: "top-right",
        autoClose: 3000,
      });
      return;
    }
    
    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: register.email,
          password: register.password,
          name: register.name,
          confirmPassword: register.confirmPassword// Ensure confirmPassword is sent
        }),
      });
  
      const data = await response.json();
  
      if (!response.ok) {
        throw new Error(data.message || "Failed to register user");
      }
  
      console.log("User added:", data);
      //alert("User registered successfully!");
      toast.success("User registered successfully!", {position:"top-right", autoClose:3000})
    } catch (error) {
      console.error("Error:", error.message);
      //alert(`Error: ${error.message}`);
      toast.error("Email Already Exist!", {position:"top-right", autoClose:3000})
    }
  };
  
  return (
    <section className="vh-100">
      <div className="container-fluid h-custom" style={{ padding: '10%' }}>
        <ToastContainer />
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col-md-9 col-lg-6 col-xl-5">
            <img
              src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
              className="img-fluid"
              alt="Sample"
            />
          </div>
          <div className="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
            <form onSubmit={handleSubmit}>
              <h2>Book Inventory Sign Up</h2>

              {/* Name Field */}
              <div className="form-outline mb-4">
                <input
                  type="text"
                  id="name"
                  name="name"
                  className="form-control form-control-lg"
                  placeholder="Enter name"
                  value={register.name}
                  onChange={handleChange}
                  style={{ border: '1px solid' }}
                  required
                />
                <label className="form-label" htmlFor="name">Name</label>
              </div>

              {/* Email Field */}
              <div className="form-outline mb-4">
                <input
                  type="email"
                  id="email"
                  name="email"
                  className="form-control form-control-lg"
                  placeholder="Enter a valid email address"
                  value={register.email}
                  onChange={handleChange}
                  style={{ border: '1px solid' }}
                  required
                />
                <label className="form-label" htmlFor="email">Email Address</label>
              </div>

              {/* Password Field */}
              <div className="form-outline mb-3">
                <input
                  type={passwordVisible ? 'text' : 'password'}
                  id="password"
                  name="password"
                  className="form-control form-control-lg"
                  placeholder="Enter password"
                  value={register.password}
                  onChange={handleChange}
                  style={{ border: '1px solid', paddingRight: '40px' }}
                  required
                />
                <label className="form-label" htmlFor="password">Password</label>
                <span
                  onClick={togglePasswordVisibility}
                  style={{
                    position: 'absolute',
                    right: '10px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    cursor: 'pointer',
                  }}
                >
                  {passwordVisible ? '🙈' : '👁️'}
                </span>
              </div>

              {/* Confirm Password Field */}
              <div className="form-outline mb-3">
                <input
                  type={passwordVisible ? 'text' : 'password'}
                  id="confirmPassword"
                  name="confirmPassword"
                  className="form-control form-control-lg"
                  placeholder="Confirm password"
                  value={register.confirmPassword}
                  onChange={handleChange}
                  style={{ border: '1px solid', paddingRight: '40px' }}
                  required
                />
                <label className="form-label" htmlFor="confirmPassword">Confirm Password</label>
                <span
                  onClick={togglePasswordVisibility}
                  style={{
                    position: 'absolute',
                    right: '10px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    cursor: 'pointer',
                  }}
                >
                  {passwordVisible ? '🙈' : '👁️'}
                </span>
              </div>

              <div className="text-center text-lg-start mt-4 pt-2">
                <button
                  type="submit"
                  className="btn btn-primary btn-lg"
                  style={{ paddingleft: '2.5rem', paddingright: '2.5rem' }}
                >
                  SignUp
                </button>
                <p className="small fw-bold mt-2 pt-1 mb-0">
                  Have an account? <a href="/" className="link-danger">Login</a>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}

export default SignUp;
