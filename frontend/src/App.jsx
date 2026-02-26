import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { MapPin, Star, Calendar, CreditCard, Search } from 'lucide-react';

// Using API Gateway URL
const API_URL = 'http://localhost:8765/api';

function App() {
  const [hotels, setHotels] = useState([]);
  const [myBookings, setMyBookings] = useState([]);
  const [loading, setLoading] = useState(false);

  // Filters
  const [location, setLocation] = useState('');
  const [checkIn, setCheckIn] = useState('');
  const [checkOut, setCheckOut] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [category, setCategory] = useState('');

  // Authentication State
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [showAuthModal, setShowAuthModal] = useState(!token);
  const [isLogin, setIsLogin] = useState(true);
  const [authForm, setAuthForm] = useState({ username: '', password: '', email: '' });

  // Default fetch
  useEffect(() => {
    if (token) {
      searchHotels();
      fetchMyBookings();
    }
  }, [token]);

  const handleAuthSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isLogin) {
        const res = await axios.post(`${API_URL}/auth/generate-token`, {
          username: authForm.username,
          password: authForm.password
        });
        setToken(res.data.token);
        localStorage.setItem('token', res.data.token);
        setShowAuthModal(false);
      } else {
        await axios.post(`${API_URL}/auth/register`, authForm);
        alert('Registered successfully! Please login.');
        setIsLogin(true);
      }
    } catch (err) {
      alert('Authentication failed: ' + (err.response?.data?.message || err.message));
    }
  };

  const logout = () => {
    setToken('');
    localStorage.removeItem('token');
    setShowAuthModal(true);
  };

  const getAuthConfig = () => ({
    headers: { Authorization: `Bearer ${token}` }
  });

  const searchHotels = async () => {
    if (!token) return;
    setLoading(true);
    try {
      // Build query string
      let query = `?location=${location}`;
      if (checkIn) query += `&checkIn=${checkIn}`;
      if (checkOut) query += `&checkOut=${checkOut}`;
      if (minPrice) query += `&minPrice=${minPrice}`;
      if (maxPrice) query += `&maxPrice=${maxPrice}`;
      if (category) query += `&category=${category}`;

      // Call advanced search API on Booking Service
      const res = await axios.get(`${API_URL}/bookings/search${query}`, getAuthConfig());
      setHotels(res.data);
    } catch (err) {
      console.error(err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        logout();
      } else {
        alert('Failed to search hotels.');
      }
    } finally {
      setLoading(false);
    }
  };

  const fetchMyBookings = async () => {
    if (!token) return;
    try {
      const res = await axios.get(`${API_URL}/bookings/my-bookings`, getAuthConfig());
      setMyBookings(res.data);
    } catch (err) {
      console.error("Failed to fetch past bookings", err);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    searchHotels();
  };

  const bookRoom = async (hotelId, roomId) => {
    if (!checkIn || !checkOut) {
      alert("Please select Check-in and Check-out dates in the sidebar first.");
      return;
    }
    try {
      const res = await axios.post(`${API_URL}/bookings`, {
        hotelId,
        roomId,
        customerName: "Current User",
        customerEmail: "user@example.com", // In a real app, pulled from user profile
        checkInDate: checkIn,
        checkOutDate: checkOut
      }, getAuthConfig());

      alert(`Booking successful! Booking ID: ${res.data.id} | Total Price: ₹${res.data.totalPrice}`);
      searchHotels(); // refresh availability
      fetchMyBookings(); // auto-fetch new bookings
    } catch (err) {
      alert('Booking failed: ' + (err.response?.data?.message || err.response?.data || err.message));
    }
  };

  return (
    <div className="app-container">
      {/* Auth Modal */}
      {showAuthModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h2>{isLogin ? 'Welcome Back' : 'Create Account'}</h2>
            <form onSubmit={handleAuthSubmit}>
              <div className="filter-group">
                <label>Username</label>
                <input
                  required
                  value={authForm.username}
                  onChange={e => setAuthForm({ ...authForm, username: e.target.value })}
                />
              </div>
              {!isLogin && (
                <div className="filter-group">
                  <label>Email</label>
                  <input
                    type="email" required
                    value={authForm.email}
                    onChange={e => setAuthForm({ ...authForm, email: e.target.value })}
                  />
                </div>
              )}
              <div className="filter-group">
                <label>Password</label>
                <input
                  type="password" required
                  value={authForm.password}
                  onChange={e => setAuthForm({ ...authForm, password: e.target.value })}
                />
              </div>
              <button type="submit" className="search-btn">
                {isLogin ? 'Login' : 'Register'}
              </button>
            </form>
            <p style={{ marginTop: '16px', textAlign: 'center', cursor: 'pointer', color: 'var(--primary)' }} onClick={() => setIsLogin(!isLogin)}>
              {isLogin ? "Don't have an account? Register" : "Already have an account? Login"}
            </p>
          </div>
        </div>
      )}

      {/* Sidebar Filters */}
      <aside className="sidebar">
        <h2>Find your stay</h2>
        <form onSubmit={handleSearch}>
          <div className="filter-group">
            <label>Location</label>
            <input
              type="text"
              placeholder="Where are you going?"
              value={location}
              onChange={e => setLocation(e.target.value)}
            />
          </div>

          <div className="filter-group">
            <label>Check-in Date</label>
            <input
              type="date"
              value={checkIn}
              onChange={e => setCheckIn(e.target.value)}
            />
          </div>

          <div className="filter-group">
            <label>Check-out Date</label>
            <input
              type="date"
              value={checkOut}
              onChange={e => setCheckOut(e.target.value)}
            />
          </div>

          <div className="filter-group" style={{ display: 'flex', gap: '8px' }}>
            <div style={{ flex: 1 }}>
              <label>Min Price (₹)</label>
              <input
                type="number"
                placeholder="0"
                value={minPrice}
                onChange={e => setMinPrice(e.target.value)}
              />
            </div>
            <div style={{ flex: 1 }}>
              <label>Max Price (₹)</label>
              <input
                type="number"
                placeholder="10000+"
                value={maxPrice}
                onChange={e => setMaxPrice(e.target.value)}
              />
            </div>
          </div>

          <div className="filter-group">
            <label>Room Category</label>
            <select value={category} onChange={e => setCategory(e.target.value)}>
              <option value="">Any Category</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
          </div>

          <button type="submit" className="search-btn">
            <Search size={18} style={{ marginRight: '8px', verticalAlign: 'middle' }} />
            Search Hotels
          </button>
        </form>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <div className="header">
          <h1>Available Hotels</h1>
          {token && <button onClick={logout} className="book-btn" style={{ backgroundColor: '#ef4444' }}>Logout</button>}
        </div>

        {loading ? (
          <div className="empty-state">Loading hotels...</div>
        ) : hotels.length === 0 ? (
          <div className="empty-state">
            <h3>No hotels found matching your criteria</h3>
            <p>Try adjusting your search filters.</p>
          </div>
        ) : (
          <div className="hotel-list">
            {hotels.map(hotel => (
              <div key={hotel.id} className="hotel-card">
                <img src={hotel.imageUrl} alt={hotel.name} className="hotel-image" />
                <div className="hotel-details">

                  <div className="hotel-header">
                    <h3 className="hotel-name">{hotel.name}</h3>
                    <div className="rating-badge">
                      <Star size={16} fill="currentColor" />
                      {hotel.rating}
                    </div>
                  </div>

                  <div className="hotel-location">
                    <MapPin size={16} />
                    {hotel.location}
                  </div>

                  <h4 className="hotel-rooms-title">Available Rooms</h4>
                  <div className="room-list">
                    {hotel.availableRooms && hotel.availableRooms.map(room => (
                      <div key={room.id} className="room-card">
                        <div className="room-info">
                          <h4>{room.category || room.type || "Standard Room"}</h4>
                          <span className="room-price">₹{room.price} <small style={{ color: 'var(--text-secondary)', fontWeight: 'normal' }}>/ night</small></span>

                          {room.facilities && (
                            <div className="facilities-list" style={{ marginBottom: '8px' }}>
                              {room.facilities.split(',').map((f, i) => (
                                <span key={i} className="facility-chip">{f.trim()}</span>
                              ))}
                            </div>
                          )}
                          <p style={{ margin: 0, fontSize: '0.9rem', color: room.availableRooms < 5 ? '#ef4444' : 'var(--success)' }}>
                            <strong>{room.availableRooms}</strong> room(s) left
                          </p>
                        </div>
                        <button
                          onClick={() => bookRoom(hotel.id, room.id)}
                          className="book-btn"
                        >
                          Book Now
                        </button>
                      </div>
                    ))}
                  </div>

                </div>
              </div>
            ))}
          </div>
        )}

        {/* My Bookings Section */}
        {token && (
          <div className="my-bookings-section" style={{ marginTop: '40px', paddingTop: '40px', borderTop: '1px solid var(--border)' }}>
            <h2>My Booked Hotels</h2>
            {myBookings.length === 0 ? (
              <p style={{ color: 'var(--text-secondary)' }}>You haven't booked any rooms yet.</p>
            ) : (
              <div className="hotel-list">
                {myBookings.map(booking => (
                  <div key={booking.id} className="hotel-card" style={{ padding: '0', display: 'flex', flexDirection: 'row', overflow: 'hidden' }}>
                    {booking.hotelImageUrl && (
                      <img src={booking.hotelImageUrl} alt={booking.hotelName} style={{ width: '200px', objectFit: 'cover' }} />
                    )}
                    <div style={{ padding: '20px', flex: 1 }}>
                      <h3>Booking #{booking.id} - {booking.hotelName || 'Hotel'}</h3>
                      <p><strong>Room:</strong> {booking.roomCategory || 'Standard'}</p>
                      <p><strong>Check-in:</strong> {booking.checkInDate} | <strong>Check-out:</strong> {booking.checkOutDate}</p>
                      <p><strong>Total Price:</strong> ₹{booking.totalPrice}</p>
                      <p><strong>Status:</strong> <span style={{ color: 'green', fontWeight: 'bold' }}>{booking.status}</span></p>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
