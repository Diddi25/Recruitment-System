import axios from 'axios';
import authHeader from './authHeader';

const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',  // API Gateway URL
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Gateway paths used by the advertisement service
 */
export const advertisementService = {
  getTest: () => apiClient.get('/advertisements/test', {headers: authHeader()}), //endpoint in AdvertisementController.java
  getAll: () => apiClient.get('/advertisements/all', {headers: authHeader()}),
  getById: (id) => apiClient.get(`/advertisements/${id}`),
  create: (data) => apiClient.post('/advertisements/create', data),
  update: (id, data) => apiClient.put(`/advertisements/${id}`, data),
  delete: (id) => apiClient.delete(`/advertisements/${id}`),
  updateStatus: (id, status) => apiClient.put(`/advertisements/${id}/status`, { status })
};

// Candidate Application Service API
export const candidateApplicationService = {
  applyForPosition: (data) => apiClient.post('/applications/apply', {headers: authHeader()}, data),
  getAllApplications: () => apiClient.get('/applications/all', {headers: authHeader()}),
  getApplicationById: (id) => apiClient.get('/applications/${id}', {headers: authHeader()})
};

// Application List Service API
// (..)

export default apiClient;