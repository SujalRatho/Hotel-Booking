import axios from 'axios';

const API_URL = 'http://localhost:8765/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to attach JWT token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor for 401 handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth APIs
export const loginUser = (username, password) =>
  api.post('/auth/generate-token', { username, password });

export const registerUser = (username, email, password) =>
  api.post('/auth/register', { username, email, password });

// Hotel Search API (via booking service)
export const searchHotels = (filters = {}) => {
  const params = new URLSearchParams();
  if (filters.location) params.append('location', filters.location);
  if (filters.checkIn) params.append('checkIn', filters.checkIn);
  if (filters.checkOut) params.append('checkOut', filters.checkOut);
  if (filters.minPrice) params.append('minPrice', filters.minPrice);
  if (filters.maxPrice) params.append('maxPrice', filters.maxPrice);
  if (filters.category) params.append('category', filters.category);
  return api.get(`/bookings/search?${params.toString()}`);
};

// Hotel APIs
export const getAllHotels = () => api.get('/hotels');
export const getHotelById = (id) => api.get(`/hotels/${id}`);
export const getRoomsByHotel = (hotelId) => api.get(`/rooms/hotel/${hotelId}`);

// Booking APIs
export const createBooking = (bookingData) =>
  api.post('/bookings', bookingData);

export const getMyBookings = () =>
  api.get('/bookings/my-bookings');

export default api;
