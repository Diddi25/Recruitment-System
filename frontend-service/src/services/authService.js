import axios from 'axios';

const API_URL = '/api'; // Relative URL

class AuthService {
  login(user) {
    return axios
      .post(API_URL + '/identification/login', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        console.log(user, response.data)
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    console.log("Registration request:", user);
    return axios.post(API_URL + 'register', {
        name: user.name,
        surname: user.lastname,
        personNumber: user.personNumber,
        email: user.email,
        username: user.username,
        password: user.password,
    }).then(response => {
        console.log("Registration response:", response);
        return response.data;
    });
  }
}

export default new AuthService();
