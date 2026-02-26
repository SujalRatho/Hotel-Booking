import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { searchHotels } from '../services/api';
import { MapPin, Star, Search, ArrowRight, Shield, Clock, Sparkles } from 'lucide-react';

const HOTEL_IMAGES = [
  '/hotel-images/grand-palace.jpg',
  '/hotel-images/taj-lakefront.jpg',
  '/hotel-images/ocean-breeze.jpg',
  '/hotel-images/mountain-view.jpg',
  '/hotel-images/royal-heritage.jpg',
  '/hotel-images/sunrise-beach.jpg',
];

const HomePage = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const [featuredHotels, setFeaturedHotels] = useState([]);
  const [searchLocation, setSearchLocation] = useState('');
  const [searchCheckIn, setSearchCheckIn] = useState('');
  const [searchCheckOut, setSearchCheckOut] = useState('');

  useEffect(() => {
    if (isAuthenticated) {
      loadFeaturedHotels();
    }
  }, [isAuthenticated]);

  const loadFeaturedHotels = async () => {
    try {
      const res = await searchHotels({});
      setFeaturedHotels(res.data.slice(0, 6));
    } catch (err) {
      console.error('Failed to load featured hotels', err);
    }
  };

  const handleHeroSearch = (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    if (searchLocation) params.set('location', searchLocation);
    if (searchCheckIn) params.set('checkIn', searchCheckIn);
    if (searchCheckOut) params.set('checkOut', searchCheckOut);
    navigate(`/search?${params.toString()}`);
  };

  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-overlay"></div>
        <div className="hero-content">
          <h1 className="hero-title">Discover Your <span className="gold-text">Perfect Stay</span></h1>
          <p className="hero-subtitle">Luxury hotels at unbeatable prices — from coastal resorts to mountain retreats</p>

          <form className="hero-search-bar" onSubmit={handleHeroSearch}>
            <div className="hero-search-field">
              <MapPin size={18} className="search-icon" />
              <input
                type="text"
                placeholder="Where are you going?"
                value={searchLocation}
                onChange={(e) => setSearchLocation(e.target.value)}
              />
            </div>
            <div className="hero-search-field">
              <input
                type="date"
                placeholder="Check-in"
                value={searchCheckIn}
                onChange={(e) => setSearchCheckIn(e.target.value)}
              />
            </div>
            <div className="hero-search-field">
              <input
                type="date"
                placeholder="Check-out"
                value={searchCheckOut}
                onChange={(e) => setSearchCheckOut(e.target.value)}
              />
            </div>
            <button type="submit" className="hero-search-btn">
              <Search size={18} />
              Search
            </button>
          </form>
        </div>
      </section>

      {/* Features Section */}
      <section className="features-section">
        <div className="features-grid">
          <div className="feature-card">
            <div className="feature-icon">
              <Shield size={32} />
            </div>
            <h3>Best Prices</h3>
            <p>Guaranteed lowest prices on premium hotels across India</p>
          </div>
          <div className="feature-card">
            <div className="feature-icon">
              <Sparkles size={32} />
            </div>
            <h3>Premium Comfort</h3>
            <p>Hand-picked luxury properties with world-class amenities</p>
          </div>
          <div className="feature-card">
            <div className="feature-icon">
              <Clock size={32} />
            </div>
            <h3>24/7 Support</h3>
            <p>Round-the-clock customer support for a seamless experience</p>
          </div>
        </div>
      </section>

      {/* Featured Hotels Section */}
      {featuredHotels.length > 0 && (
        <section className="featured-section">
          <div className="section-header">
            <h2>Featured <span className="gold-text">Hotels</span></h2>
            <Link to="/search" className="view-all-link">
              View All <ArrowRight size={16} />
            </Link>
          </div>

          <div className="featured-grid">
            {featuredHotels.map((hotel, index) => (
              <div key={hotel.id} className="featured-hotel-card">
                <div className="featured-hotel-image">
                  <img
                    src={hotel.imageUrl || HOTEL_IMAGES[index % HOTEL_IMAGES.length]}
                    alt={hotel.name}
                    onError={(e) => { e.target.src = `https://picsum.photos/seed/${hotel.id}/400/300`; }}
                  />
                  <div className="featured-rating">
                    <Star size={14} fill="currentColor" />
                    {hotel.rating}
                  </div>
                </div>
                <div className="featured-hotel-info">
                  <h3>{hotel.name}</h3>
                  <p className="featured-location">
                    <MapPin size={14} />
                    {hotel.location}
                  </p>
                  {hotel.availableRooms && hotel.availableRooms.length > 0 && (
                    <p className="featured-price">
                      From <span className="gold-text">₹{Math.min(...hotel.availableRooms.map(r => r.price))}</span> / night
                    </p>
                  )}
                  <Link to={`/search?location=${hotel.location?.split(',')[0]}`} className="btn-view-details">
                    View Details <ArrowRight size={14} />
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* CTA Section */}
      <section className="cta-section">
        <h2>Ready to <span className="gold-text">Explore?</span></h2>
        <p>Find the best deals on luxury hotels across India</p>
        <Link to={isAuthenticated ? '/search' : '/login'} className="cta-btn">
          {isAuthenticated ? 'Start Searching' : 'Sign In to Begin'}
          <ArrowRight size={18} />
        </Link>
      </section>
    </div>
  );
};

export default HomePage;
