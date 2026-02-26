import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { searchHotels, createBooking } from '../services/api';
import { MapPin, Star, Search, Filter, ChevronDown, ChevronUp } from 'lucide-react';

const SearchPage = () => {
  const [searchParams] = useSearchParams();
  const { isAuthenticated } = useAuth();

  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(false);
  const [expandedHotel, setExpandedHotel] = useState(null);

  // Filters
  const [location, setLocation] = useState(searchParams.get('location') || '');
  const [checkIn, setCheckIn] = useState(searchParams.get('checkIn') || '');
  const [checkOut, setCheckOut] = useState(searchParams.get('checkOut') || '');
  const [minPrice, setMinPrice] = useState(searchParams.get('minPrice') || '');
  const [maxPrice, setMaxPrice] = useState(searchParams.get('maxPrice') || '');
  const [category, setCategory] = useState(searchParams.get('category') || '');

  useEffect(() => {
    if (isAuthenticated) {
      handleSearch();
    }
  }, [isAuthenticated]);

  const handleSearch = async (e) => {
    if (e) e.preventDefault();
    if (!isAuthenticated) return;
    setLoading(true);
    try {
      const res = await searchHotels({ location, checkIn, checkOut, minPrice, maxPrice, category });
      setHotels(res.data);
    } catch (err) {
      console.error('Search failed:', err);
      alert('Failed to search hotels.');
    } finally {
      setLoading(false);
    }
  };

  const handleBookRoom = async (hotelId, roomId) => {
    if (!checkIn || !checkOut) {
      alert('Please select Check-in and Check-out dates first.');
      return;
    }
    try {
      const res = await createBooking({
        hotelId,
        roomId,
        customerName: 'Current User',
        customerEmail: 'user@example.com',
        checkInDate: checkIn,
        checkOutDate: checkOut,
      });
      alert(`Booking successful! Booking ID: ${res.data.id} | Total: ₹${res.data.totalPrice}`);
      handleSearch();
    } catch (err) {
      alert('Booking failed: ' + (err.response?.data?.message || err.response?.data || err.message));
    }
  };

  const toggleHotelExpand = (hotelId) => {
    setExpandedHotel(expandedHotel === hotelId ? null : hotelId);
  };

  return (
    <div className="search-page">
      {/* Sidebar Filters */}
      <aside className="search-sidebar">
        <div className="sidebar-header">
          <Filter size={20} />
          <h2>Filters</h2>
        </div>

        <form onSubmit={handleSearch}>
          <div className="filter-group">
            <label>Location</label>
            <input
              type="text"
              placeholder="City or area..."
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
          </div>

          <div className="filter-group">
            <label>Check-in Date</label>
            <input
              type="date"
              value={checkIn}
              onChange={(e) => setCheckIn(e.target.value)}
            />
          </div>

          <div className="filter-group">
            <label>Check-out Date</label>
            <input
              type="date"
              value={checkOut}
              onChange={(e) => setCheckOut(e.target.value)}
            />
          </div>

          <div className="filter-group price-range">
            <label>Price Range (₹)</label>
            <div className="price-inputs">
              <input
                type="number"
                placeholder="Min"
                value={minPrice}
                onChange={(e) => setMinPrice(e.target.value)}
              />
              <span className="price-separator">—</span>
              <input
                type="number"
                placeholder="Max"
                value={maxPrice}
                onChange={(e) => setMaxPrice(e.target.value)}
              />
            </div>
          </div>

          <div className="filter-group">
            <label>Room Category</label>
            <select value={category} onChange={(e) => setCategory(e.target.value)}>
              <option value="">Any Category</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
          </div>

          <button type="submit" className="search-btn">
            <Search size={18} />
            Search Hotels
          </button>
        </form>
      </aside>

      {/* Results */}
      <main className="search-results">
        <div className="results-header">
          <h1>Available Hotels</h1>
          <span className="results-count">{hotels.length} hotels found</span>
        </div>

        {!isAuthenticated ? (
          <div className="empty-state">
            <Search size={48} className="empty-icon" />
            <h3>Sign in to search hotels</h3>
            <p>Please <a href="/login" style={{ color: 'var(--gold)', fontWeight: 700 }}>log in</a> to search and book hotels.</p>
          </div>
        ) : loading ? (
          <div className="loading-state">
            <div className="spinner"></div>
            <p>Searching for the best hotels...</p>
          </div>
        ) : hotels.length === 0 ? (
          <div className="empty-state">
            <Search size={48} className="empty-icon" />
            <h3>No hotels found</h3>
            <p>Try adjusting your search filters or click "Search Hotels" to see all available hotels.</p>
          </div>
        ) : (
          <div className="hotel-results-list">
            {hotels.map((hotel) => (
              <div key={hotel.id} className="hotel-result-card">
                <div className="hotel-result-main" onClick={() => toggleHotelExpand(hotel.id)}>
                  <img
                    src={hotel.imageUrl}
                    alt={hotel.name}
                    className="hotel-result-image"
                    onError={(e) => { e.target.src = `https://picsum.photos/seed/${hotel.id}/400/250`; }}
                  />
                  <div className="hotel-result-info">
                    <div className="hotel-result-header">
                      <h3>{hotel.name}</h3>
                      <div className="rating-badge">
                        <Star size={14} fill="currentColor" />
                        {hotel.rating}
                      </div>
                    </div>
                    <p className="hotel-result-location">
                      <MapPin size={14} />
                      {hotel.location}
                    </p>
                    {hotel.availableRooms && hotel.availableRooms.length > 0 && (
                      <div className="hotel-result-meta">
                        <span className="hotel-result-price">
                          From ₹{Math.min(...hotel.availableRooms.map(r => r.price))} / night
                        </span>
                        <span className="hotel-result-rooms">
                          {hotel.availableRooms.length} room type(s) available
                        </span>
                      </div>
                    )}
                    <button className="expand-rooms-btn">
                      {expandedHotel === hotel.id ? (
                        <>Hide Rooms <ChevronUp size={16} /></>
                      ) : (
                        <>View Rooms <ChevronDown size={16} /></>
                      )}
                    </button>
                  </div>
                </div>

                {/* Expandable Rooms Section */}
                {expandedHotel === hotel.id && hotel.availableRooms && (
                  <div className="rooms-expanded">
                    <h4 className="rooms-title">Available Rooms</h4>
                    <div className="rooms-grid">
                      {hotel.availableRooms.map((room) => (
                        <div key={room.id} className="room-card">
                          <div className="room-card-header">
                            <h4>{room.category || 'Standard Room'}</h4>
                            <span className="room-price-tag">₹{room.price}<small>/night</small></span>
                          </div>

                          {room.facilities && (
                            <div className="facilities-list">
                              {room.facilities.split(',').map((f, i) => (
                                <span key={i} className="facility-chip">{f.trim()}</span>
                              ))}
                            </div>
                          )}

                          <div className="room-card-footer">
                            <span className={`availability ${room.availableRooms < 5 ? 'low' : ''}`}>
                              {room.availableRooms} room(s) left
                            </span>
                            <button
                              onClick={(e) => {
                                e.stopPropagation();
                                handleBookRoom(hotel.id, room.id);
                              }}
                              className="btn-book"
                            >
                              Book Now
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                )}
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
};

export default SearchPage;
