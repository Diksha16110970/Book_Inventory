// import React, { useState } from 'react';
// import './Login.css'; // Reusing the same styles
// import { toast, ToastContainer } from "react-toastify";
// import "react-toastify/dist/ReactToastify.css";
// import { useNavigate } from "react-router-dom";

// function ResetPassword() {
//     const [password, setPassword] = useState("");
//     //const [confirmPassword, setConfirmPassword] = useState("");
//     const navigate = useNavigate();

//     const handlePasswordChange = (e) => {
//         setPassword(e.target.value);
//     };

//     // const handleConfirmPasswordChange = (e) => {
//     //     setConfirmPassword(e.target.value);
//     // };

//     const handleResetPassword = async (e) => {
//         e.preventDefault();
        
//         if (!password) {
//             toast.error("Both fields are required!", { position: "top-right", autoClose: 3000 });
//             return;
//         }

        
//         try {
//             const response = await fetch("http://localhost:8080/api/auth/reset-password", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/json",
//                 },
//                 body: JSON.stringify({ password }),
//             });

//             if (!response.ok) {
//                 throw new Error(`HTTP error! status: ${response.status}`);
//             }

//             toast.success("Password reset successfully!", { position: "top-right", autoClose: 3000 });

//             setTimeout(() => {
//                 navigate("/");
//             }, 2000);

//         } catch (error) {
//             console.error("Error:", error.message);
//             toast.error("Failed to reset password!", { position: "top-right", autoClose: 3000 });
//         }
//     };

//     return (
//         <section className="vh-100">
//             <div className="container-fluid h-custom" style={{ padding: '10%' }}>
//                 <ToastContainer />
//                 <div className="row d-flex justify-content-center align-items-center h-100">
//                     <div className="col-md-9 col-lg-6 col-xl-5">
//                         <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
//                             className="img-fluid" alt="Sample" />
//                     </div>
//                     <div className="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
//                         <form onSubmit={handleResetPassword}>
//                             <h2>Reset Password</h2>

//                             <div className="form-outline mb-4">
//                                 <input
//                                     type="password"
//                                     id="password"
//                                     className="form-control form-control-lg"
//                                     placeholder="Enter new password"
//                                     value={password}
//                                     onChange={handlePasswordChange}
//                                     required
//                                     style={{ border: '1px solid' }}
//                                 />
//                                 <label className="form-label" htmlFor="password">Set Password</label>
//                             </div>

                           

//                             <div className="text-center text-lg-start mt-4 pt-2">
//                                 <button
//                                     type="submit"
//                                     className="btn btn-primary btn-lg"
//                                     style={{ paddingLeft: '2.5rem', paddingRight: '2.5rem' }}>
//                                     Reset Password
//                                 </button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </section>
//     );
// }

// export default ResetPassword;


import React, { useState } from 'react';
import './Login.css'; 
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate, useLocation } from "react-router-dom";

function ResetPassword() {
    const [password, setPassword] = useState("");
    const location = useLocation();
    const navigate = useNavigate();

    // Get email & OTP from Forget.js
    const email = location.state?.email || "";
    const otp = location.state?.otp || "";

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleResetPassword = async (e) => {
        e.preventDefault();
        
        if (!password) {
            toast.error("New password is required!", { position: "top-right", autoClose: 3000 });
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/auth/reset-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, otp, password }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            toast.success("Password reset successfully!", { position: "top-right", autoClose: 3000 });

            setTimeout(() => {
                navigate("/");
            }, 2000);

        } catch (error) {
            console.error("Error:", error.message);
            toast.error("Failed to reset password!", { position: "top-right", autoClose: 3000 });
        }
    };

    return (
        <section className="vh-100">
            <div className="container-fluid h-custom" style={{ padding: '10%' }}>
                <ToastContainer />
                <div className="row d-flex justify-content-center align-items-center h-100">
                    <div className="col-md-9 col-lg-6 col-xl-5">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                            className="img-fluid" alt="Sample" />
                    </div>
                    <div className="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <form onSubmit={handleResetPassword}>
                            <h2>Reset Password</h2>

                            <div className="form-outline mb-4">
                                <input
                                    type="password"
                                    id="password"
                                    className="form-control form-control-lg"
                                    placeholder="Enter new password"
                                    value={password}
                                    onChange={handlePasswordChange}
                                    required
                                    style={{ border: '1px solid' }}
                                />
                                <label className="form-label" htmlFor="password">Set Password</label>
                            </div>

                            <div className="text-center text-lg-start mt-4 pt-2">
                                <button type="submit" className="btn btn-primary btn-lg">
                                    Reset Password
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default ResetPassword;
