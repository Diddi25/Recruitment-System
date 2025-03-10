import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8081/api/test/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'all');
  }

  getUserBoard() {
    return axios.get(API_URL + 'user', { headers: authHeader() });
  }

  getRecruiterBoard() {
    return axios.get(API_URL + 'rec', { headers: authHeader() });
  }
}

export default new UserService();