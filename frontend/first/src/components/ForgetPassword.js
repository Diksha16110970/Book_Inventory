// import React, { useState } from 'react';
// import './Login.css'; // Reusing the same styles
// import { toast, ToastContainer } from "react-toastify";
// import "react-toastify/dist/ReactToastify.css";
// import { useNavigate } from "react-router-dom";

// function Forget() {
//     const [email, setEmail] = useState("");
//     const navigate = useNavigate();

//     const handleEmailChange = (e) => {
//         setEmail(e.target.value);
//     };

//     const handleSendOTP = async (e) => {
//         e.preventDefault();
        
//         if (!email) {
//             toast.error("Email is required!", { position: "top-right", autoClose: 3000 });
//             return;
//         }

//         try {
//             const response = await fetch("http://localhost:8080/api/auth/forgot-password", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/json",
//                 },
//                 body: JSON.stringify({ email }),
//             });

//             if (!response.ok) {
//                 throw new Error(`HTTP error! status: ${response.status}`);
//             }

//             toast.success("OTP sent successfully!", { position: "top-right", autoClose: 3000 });

//         } catch (error) {
//             console.error("Error:", error.message);
//             toast.error("Failed to send OTP!", { position: "top-right", autoClose: 3000 });
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
//                         <form onSubmit={handleSendOTP}>
//                             <h2>Forget Password</h2>

//                             <div className="form-outline mb-4">
//                                 <input
//                                     type="email"
//                                     id="email"
//                                     className="form-control form-control-lg"
//                                     placeholder="Enter your email"
//                                     value={email}
//                                     onChange={handleEmailChange}
//                                     required
//                                     style={{ border: '1px solid' }}
//                                 />
//                                 <label className="form-label" htmlFor="email">Enter Email</label>
//                             </div>

//                             <div className="text-center text-lg-start mt-4 pt-2">
//                                 <button
//                                     type="button"
//                                     className="btn btn-secondary btn-lg mb-2"
//                                     style={{ paddingLeft: '2.5rem', paddingRight: '2.5rem' }}
//                                     onClick={() => navigate("/")}>
//                                     Login
//                                 </button>
//                                 <br />
//                                 <button
//                                     type="submit"
//                                     className="btn btn-primary btn-lg"
//                                     style={{ paddingLeft: '2.5rem', paddingRight: '2.5rem' }}>
//                                     Send OTP
//                                 </button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </section>
//     );
// }

// export default Forget;


import React, { useState } from 'react';
import './Login.css'; 
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

function Forget() {
    const [email, setEmail] = useState("");
    const [otpSent, setOtpSent] = useState(false); // Track OTP sent
    const [otp, setOtp] = useState(""); // OTP input field
    const navigate = useNavigate();

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handleOtpChange = (e) => {
        setOtp(e.target.value);
    };

    const handleSendOTP = async (e) => {
        e.preventDefault();

        if (!email) {
            toast.error("Email is required!", { position: "top-right", autoClose: 3000 });
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/auth/forgot-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            toast.success("OTP sent successfully!", { position: "top-right", autoClose: 3000 });
            setOtpSent(true);

        } catch (error) {
            console.error("Error:", error.message);
            toast.error("Failed to send OTP!", { position: "top-right", autoClose: 3000 });
        }
    };

    const handleVerifyOtp = () => {
        if (!otp) {
            toast.error("Enter OTP to continue!", { position: "top-right", autoClose: 3000 });
            return;
        }

        navigate("/reset-password", { state: { email, otp } });
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
                        <form onSubmit={handleSendOTP}>
                            <h2>Forget Password</h2>

                            <div className="form-outline mb-4">
                                <input
                                    type="email"
                                    id="email"
                                    className="form-control form-control-lg"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={handleEmailChange}
                                    required
                                    style={{ border: '1px solid' }}
                                />
                                <label className="form-label" htmlFor="email">Enter Email</label>
                            </div>

                            {otpSent && (
                                <div className="form-outline mb-4">
                                    <input
                                        type="text"
                                        id="otp"
                                        className="form-control form-control-lg"
                                        placeholder="Enter OTP"
                                        value={otp}
                                        onChange={handleOtpChange}
                                        required
                                        style={{ border: '1px solid' }}
                                    />
                                    <label className="form-label" htmlFor="otp">Enter OTP</label>
                                </div>
                            )}

                            <div className="text-center text-lg-start mt-4 pt-2">
                                {!otpSent ? (
                                    <button type="submit" className="btn btn-primary btn-lg">
                                        Send OTP
                                    </button>
                                ) : (
                                    <button type="button" className="btn btn-success btn-lg" onClick={handleVerifyOtp}>
                                        Verify OTP
                                    </button>
                                )}
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default Forget;

