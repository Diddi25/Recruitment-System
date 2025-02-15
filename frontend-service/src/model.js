import {login} from "./springRequests"
export default {
    user: {username: null, password: null, isLoggedIn: false},

    async submitLoginCredentials(username, password) {
        let result = await login(username, password);
        this.user.isLoggedIn = result.success;
    },
}

