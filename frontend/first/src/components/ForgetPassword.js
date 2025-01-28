import React, { useState } from 'react';
import axios from 'axios';

function ForgetPassword() {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isOtpSent, setIsOtpSent] = useState(false);

  const handleSendOtp = async () => {
    try {
      const response = await axios.post('http://localhost:8080/auth/forgotPassword', { email });
      alert(response.data);
      setIsOtpSent(true);
    } catch (error) {
      alert('Error sending OTP. Please try again.');
    }
  };

  const handleResetPassword = async () => {
    if (newPassword !== confirmPassword) {
      alert("Passwords don't match");
      return;
    }

    try {
      const response = await axios.post(
        `http://localhost:8080/auth/resetPassword?email=${email}&otp=${otp}&newPassword=${newPassword}&confirmPassword=${confirmPassword}`
      );
      alert(response.data);
      if (response.data === 'Password reset successful') {
        window.location.href = '/login';
      }
    } catch (error) {
      alert('Error resetting password. Please try again.');
    }
  };

  return (
    <section className="vh-100">
      <div className="container-fluid h-custom" style={{ padding: '10%' }}>
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
            <form>
              <h2>Forget Password</h2>

              {!isOtpSent ? (
                <>
                  <div className="form-outline mb-4">
                    <input
                      type="email"
                      className="form-control form-control-lg"
                      placeholder="Enter your email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                    <label className="form-label">Email Address</label>
                  </div>
                  <button
                    type="button"
                    className="btn btn-primary btn-lg"
                    onClick={handleSendOtp}
                  >
                    Send OTP
                  </button>
                </>
              ) : (
                <>
                  <div className="form-outline mb-4">
                    <input
                      type="text"
                      className="form-control form-control-lg"
                      placeholder="Enter OTP"
                      value={otp}
                      onChange={(e) => setOtp(e.target.value)}
                    />
                    <label className="form-label">Verification Code</label>
                  </div>
                  <div className="form-outline mb-4">
                    <input
                      type="password"
                      className="form-control form-control-lg"
                      placeholder="New Password"
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                    />
                    <label className="form-label">New Password</label>
                  </div>
                  <div className="form-outline mb-4">
                    <input
                      type="password"
                      className="form-control form-control-lg"
                      placeholder="Confirm Password"
                      value={confirmPassword}
                      onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                    <label className="form-label">Confirm Password</label>
                  </div>
                  <button
                    type="button"
                    className="btn btn-primary btn-lg"
                    onClick={handleResetPassword}
                  >
                    Set Password
                  </button>
                </>
              )}
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}

export default ForgetPassword;
