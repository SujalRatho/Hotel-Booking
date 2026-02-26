import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-brand">
          <Link to="/" className="footer-logo">
            <span className="logo-icon">✦</span>
            StayVista
          </Link>
          <p className="footer-tagline">Your luxury journey begins here</p>
        </div>

        <div className="footer-links-group">
          <h4>Quick Links</h4>
          <Link to="/">Home</Link>
          <Link to="/search">Search Hotels</Link>
          <Link to="/my-bookings">My Bookings</Link>
        </div>

        <div className="footer-links-group">
          <h4>Support</h4>
          <a href="#">Help Center</a>
          <a href="#">Contact Us</a>
          <a href="#">Privacy Policy</a>
        </div>

        <div className="footer-links-group">
          <h4>Contact</h4>
          <p>support@stayvista.com</p>
          <p>+91 98765 43210</p>
        </div>
      </div>

      <div className="footer-bottom">
        <p>&copy; 2026 StayVista. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
