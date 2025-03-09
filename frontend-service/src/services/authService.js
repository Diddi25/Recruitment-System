import axios from 'axios';

const API_URL = 'http://localhost:8081/api'; 

/*
* Used to interact with the identifiactiono service.
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
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }

/*
* Logs out the current user.
*/
  logout() {
    localStorage.removeItem('user');
  }

/*
* Registers an user.
*/
  register(user) {
    console.log("Registration request:", user);
    return axios.post(API_URL + '/identification/register', {
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
