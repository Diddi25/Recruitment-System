import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',  // API Gateway URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export const advertisementService = {
  getAll: () => apiClient.get('/advertisements'),
};

export default apiClient;