import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/test/';


/*REMOVE THIS?*/
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