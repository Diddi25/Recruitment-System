import axios from 'axios';

const API_URL = '/api';

/*
* Used to interact with the indentification service.
*/
class AuthService {
  login(user) {
    return axios
      .post(API_URL + '/identification/login', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          sessionStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }

/*
* Logs out the current user.
*/
  logout() {
    sessionStorage.removeItem('user');
  }

/*
* Registers an user.
*/
  register(user) {
    return axios.post(API_URL + '/identification/register', {
        name: user.name,
        surname: user.lastname,
        personNumber: user.personNumber,
        email: user.email,
        username: user.username,
        password: user.password,
    }).then(response => {
        return response.data;
    });
  }
}

export default new AuthService();
