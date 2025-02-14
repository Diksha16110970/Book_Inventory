//import React from 'react';
import React, { useState,useEffect } from 'react'; 
//import jwt_decode from "jwt-decode";
//import jwt_decode from "jwt-decode"; // Already imported
import { jwtDecode } from "jwt-decode";

//import React, { useState } from 'react';
import './Login.css';
import { useNavigate } from 'react-router-dom';

//import { redirect } from 'react-router-dom';
//import axios from "axios";
import {toast, ToastContainer} from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
function Login(){

    const[email , setEmailValue] = useState("");
    const[password , setPasswordValue] = useState("");
    const [passwordVisible, setPasswordVisible] = useState(false);
   
    const setEmail  = (e) => {
        setEmailValue(e.target.value)
    }
    const setPassword  = (e) =>{
        setPasswordValue(e.target.value)
    }

    const redirectToRegister = (e) =>{
          window.location.href = "/signup";
    }
    const togglePasswordVisibility = () => {
      setPasswordVisible(!passwordVisible);
    };
    const navigate = useNavigate();

    
    useEffect(() => {
      const token = localStorage.getItem("token");
      if (token) {
        navigate("/"); // Redirect to dashboard if token exists
      }
    }, [navigate]);


    
    const handleSubmit = async (e) => {
    e.preventDefault();
    
    const data = {
        email: email,
        password: password,
    };
    
    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json(); // Parse JSON response
        if (result && result.token) {
            const decodedToken = jwtDecode(result.token); // Correct usage
            console.log("Decoded Token:", decodedToken);

            // Store in localStorage if needed
            localStorage.setItem("token", result.token);
            localStorage.setItem("user", JSON.stringify(decodedToken));

            toast.success("Login Successful", { position: "top-right", autoClose: 3000 });
            navigate("/dashboard", { state: { message: "Login Successful!" } });
        } else {
            toast.error("Invalid email or password!", { position: "top-right", autoClose: 3000 });
        }
    } catch (error) {
        console.error("Error:", error.message);
        toast.error("Invalid email or password!", { position: "top-right", autoClose: 3000 });
    }
};

  
   
  return (
    <section className="vh-100">
    <div className="container-fluid h-custom " style={{padding:'10%'}}>
      <ToastContainer />
      <div className="row d-flex justify-content-center align-items-center h-100">
        <div className="col-md-9 col-lg-6 col-xl-5">
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
            className="img-fluid" alt="Sample image"/>
        </div>
        <div className="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
          <form onSubmit={handleSubmit}> 
        <h2>Tech Tian Book Inventory Login</h2>

        <div data-mdb-input-init className="form-outline mb-4">
          <input
            type="email"
            id="form3Example3"
            className="form-control form-control-lg"
            placeholder="Enter a valid email address"
            value={email}
            onChange={setEmail}
            style={{ border: '1px solid' }}
            required 
          />
          { <label className="form-label" htmlFor="form3Example3">Enter Email</label> }
        </div>

        <div data-mdb-input-init className="form-outline mb-3">
          <input
            //type="password"
            type={passwordVisible ? 'text' : 'password'}
            id="form3Example4"
            className="form-control form-control-lg"
            placeholder="Enter password"
            value={password}
            onChange={setPassword}
            style={{ border: '1px solid', paddingRight: '40px'}}
            required  
          />
          { <label className="form-label" htmlFor="form3Example4">Password</label> }
          

                {/* Eye icon to toggle password visibility */}
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
                  {passwordVisible ? '👁️' : '🙈' } {/* Change icon based on visibility */}
                </span>
              

        </div>

        <div className="d-flex justify-content-between align-items-center">
          <div className="form-check mb-0">
            <input className="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
            <label className="form-check-label" htmlFor="form2Example3">
              Remember me
            </label>
          </div>
          <a href="/forgot" className="text-body">Forgot password?</a>
        </div>

        <div className="text-center text-lg-start mt-4 pt-2">
          <button
            type="submit"
            className="btn btn-primary btn-lg"
            style={{ paddingleft: '2.5rem', paddingright: '2.5rem' }}>
            Login
          </button>
          <p className="small fw-bold mt-2 pt-1 mb-0">
            Don't have an account? <a href="/signup" className="link-danger" onClick={redirectToRegister}>Register</a>
          </p>
        </div>
    </form>

        </div>
      </div>
    </div>
    
  </section>
  );
}

export default Login;


