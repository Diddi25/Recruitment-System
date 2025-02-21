import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',  // API Gateway URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export const advertisementService = {
  getTest: () => apiClient.get('/advertisements/test'),
  getAll: () => apiClient.get('/advertisements/all'),
  getById: (id) => apiClient.get(`/advertisements/${id}`),
  create: (data) => apiClient.post('/advertisements/create', data),
  update: (id, data) => apiClient.put(`/advertisements/${id}`, data),
  delete: (id) => apiClient.delete(`/advertisements/${id}`),
  updateStatus: (id, status) => apiClient.put(`/advertisements/${id}/status`, { status })
};

// Candidate Application Service API
export const candidateApplicationService = {
  applyForPosition: (data) => apiClient.post('/applications', data),
  getAllApplications: () => apiClient.get('/applications'),
  getApplicationById: (id) => apiClient.get(`/applications/${id}`)
};

// Application List Service API
// (..)

export default apiClient;