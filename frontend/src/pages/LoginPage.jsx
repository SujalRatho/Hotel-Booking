import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { User, Mail, Lock, Eye, EyeOff } from 'lucide-react';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login, register, loading } = useAuth();

  const [isLoginMode, setIsLoginMode] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setError('');
    setSuccess('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (isLoginMode) {
      const result = await login(formData.username, formData.password);
      if (result.success) {
        navigate('/search');
      } else {
        setError(result.message);
      }
    } else {
      const result = await register(formData.username, formData.email, formData.password);
      if (result.success) {
        setSuccess('Account created! Please sign in.');
        setIsLoginMode(true);
        setFormData({ ...formData, password: '' });
      } else {
        setError(result.message);
      }
    }
  };

  const toggleMode = () => {
    setIsLoginMode(!isLoginMode);
    setError('');
    setSuccess('');
    setFormData({ username: '', email: '', password: '' });
  };

  return (
    <div className="login-page">
      <div className="login-left">
        <div className="login-left-overlay"></div>
        <div className="login-left-content">
          <h2 className="login-brand">
            <span className="logo-icon">✦</span> StayVista
          </h2>
          <p className="login-tagline">Your luxury journey<br />begins here</p>
        </div>
      </div>

      <div className="login-right">
        <div className="login-form-container">
          <h2>{isLoginMode ? 'Welcome Back' : 'Create Account'}</h2>
          <p className="login-form-subtitle">
            {isLoginMode ? 'Sign in to your account' : 'Join StayVista today'}
          </p>

          {error && <div className="auth-message auth-error">{error}</div>}
          {success && <div className="auth-message auth-success">{success}</div>}

          <form onSubmit={handleSubmit} className="login-form">
            <div className="input-group">
              <User size={18} className="input-icon" />
              <input
                type="text"
                name="username"
                placeholder="Username"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>

            {!isLoginMode && (
              <div className="input-group">
                <Mail size={18} className="input-icon" />
                <input
                  type="email"
                  name="email"
                  placeholder="Email address"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            )}

            <div className="input-group">
              <Lock size={18} className="input-icon" />
              <input
                type={showPassword ? 'text' : 'password'}
                name="password"
                placeholder="Password"
                value={formData.password}
                onChange={handleChange}
                required
              />
              <button
                type="button"
                className="password-toggle"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>

            <button type="submit" className="btn-auth-submit" disabled={loading}>
              {loading ? 'Please wait...' : isLoginMode ? 'Sign In' : 'Create Account'}
            </button>
          </form>

          <p className="auth-toggle">
            {isLoginMode ? "Don't have an account? " : 'Already have an account? '}
            <button type="button" onClick={toggleMode} className="auth-toggle-btn">
              {isLoginMode ? 'Register' : 'Sign In'}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
