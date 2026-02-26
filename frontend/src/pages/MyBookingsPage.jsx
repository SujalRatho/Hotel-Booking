import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { getMyBookings } from '../services/api';
import { Calendar, MapPin, CreditCard, ArrowRight } from 'lucide-react';
import { Link } from 'react-router-dom';

const MyBookingsPage = () => {
  const { isAuthenticated } = useAuth();
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (isAuthenticated) {
      fetchBookings();
    }
  }, [isAuthenticated]);

  const fetchBookings = async () => {
    try {
      const res = await getMyBookings();
      setBookings(res.data);
    } catch (err) {
      console.error('Failed to fetch bookings', err);
    } finally {
      setLoading(false);
    }
  };

  const getStatusClass = (status) => {
    if (!status) return '';
    const s = status.toLowerCase();
    if (s === 'confirmed') return 'status-confirmed';
    if (s === 'pending') return 'status-pending';
    if (s === 'cancelled') return 'status-cancelled';
    return '';
  };

  if (loading) {
    return (
      <div className="bookings-page">
        <div className="loading-state">
          <div className="spinner"></div>
          <p>Loading your bookings...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="bookings-page">
      <div className="bookings-header">
        <h1>My <span className="gold-text">Bookings</span></h1>
        <p className="bookings-subtitle">Your booking history at a glance</p>
      </div>

      {bookings.length === 0 ? (
        <div className="empty-bookings">
          <Calendar size={64} className="empty-icon" />
          <h3>No bookings yet</h3>
          <p>Start exploring amazing hotels and book your perfect stay</p>
          <Link to="/search" className="cta-btn">
            Start Exploring <ArrowRight size={18} />
          </Link>
        </div>
      ) : (
        <div className="bookings-list">
          {bookings.map((booking) => (
            <div key={booking.id} className="booking-card">
              {booking.hotelImageUrl && (
                <img
                  src={booking.hotelImageUrl}
                  alt={booking.hotelName}
                  className="booking-hotel-image"
                  onError={(e) => { e.target.src = `https://picsum.photos/seed/${booking.id}/300/200`; }}
                />
              )}
              <div className="booking-details">
                <div className="booking-top">
                  <span className="booking-id-badge">Booking #{booking.id}</span>
                  <span className={`booking-status ${getStatusClass(booking.status)}`}>
                    {booking.status || 'Confirmed'}
                  </span>
                </div>

                <h3 className="booking-hotel-name">{booking.hotelName || 'Hotel'}</h3>
                <p className="booking-room-type">{booking.roomCategory || 'Standard Room'}</p>

                <div className="booking-dates">
                  <div className="booking-date">
                    <Calendar size={14} />
                    <span>Check-in: <strong>{booking.checkInDate}</strong></span>
                  </div>
                  <div className="booking-date">
                    <Calendar size={14} />
                    <span>Check-out: <strong>{booking.checkOutDate}</strong></span>
                  </div>
                </div>

                <div className="booking-price">
                  <CreditCard size={16} />
                  <span>Total: <strong className="gold-text">₹{booking.totalPrice}</strong></span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyBookingsPage;
