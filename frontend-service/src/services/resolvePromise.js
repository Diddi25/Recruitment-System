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

  getPaged: (page = 0, size = 20) =>
    apiClient.get('/advertisements/all?pageNumber=' + page + '&pageSize=' + size, {headers: authHeader()}),


  // getPaged: (page = 0, size = 100) =>
  //   apiClient.get(`/advertisements?pageNumber=${page}&pageSize=${size}`, {headers: authHeader()}),

  getById: (id) => apiClient.get('/advertisements/${id}', {headers: authHeader()}),
  create: (data) => apiClient.post('/advertisements', data, {headers: authHeader()}),
  update: (id, data) => apiClient.put('/advertisements/${id}', data, {headers: authHeader()}),
  delete: (id) => apiClient.delete('/advertisements/${id}', {headers: authHeader()}),
  getByPerson: (personId) => apiClient.get('/advertisements/by-person/${personId}', {headers: authHeader()}),

};

// Candidate Application Service API
export const candidateApplicationService = {
  applyForPosition: (data) => apiClient.post('/applications/apply', data, {headers: authHeader()}),
  getAllApplications: () => apiClient.get('/applications/all', {headers: authHeader()}),
  getApplicationById: (id) => apiClient.get('/applications/${id}', {headers: authHeader()})
};

export default apiClient;