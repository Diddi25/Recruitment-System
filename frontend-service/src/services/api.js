import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',  // API Gateway URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export const advertisementService = {
  getTest: () => apiClient.get('/advertisements/test'), //endpoint in AdvertisementController.java
  getAll: () => apiClient.get('/advertisements/all'),
  getById: (id) => apiClient.get(`/advertisements/${id}`),
  create: (data) => apiClient.post('/advertisements/create', data),
  update: (id, data) => apiClient.put(`/advertisements/${id}`, data),
  delete: (id) => apiClient.delete(`/advertisements/${id}`),
  updateStatus: (id, status) => apiClient.put(`/advertisements/${id}/status`, { status })
};

export const identificationService = {
  getAll: (id) => apiClient.get('/test/all'),
  getUser: (id) => apiClient.get('/test/user'),
  getRecruiter: (id) => apiClient.get('/test/rec'),
  login: () => apiClient.post('/identification/login'), //endpoint in IdentificationController.java
  register: () => apiClient.post('/identification/register'),
};

export default apiClient;