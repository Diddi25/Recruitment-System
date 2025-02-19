import {login} from "./springRequests"
export default {
    user: {username: null, password: null, isLoggedIn: null},
    flags: {incorrectLoginCredentials: false, usernameAlreadyExists: null},

    //Test applications
    applications: [{name: "Anna", lastName: "Andersson", status: "unhandled", applicationId: 1}, 
        {name: "Bertil", lastName: "Bengtsson", status: "unhandled", applicationId: 2}],

    async submitLoginCredentials(username, password) {
        this.flags.incorrectLoginCredentials = null;
        let result = await login(username, password);
        this.user.isLoggedIn = result.success;
    },

    async submitRegistrationInfo(userInfo) {
        this.flags.usernameAlreadyExists = null;
        console.log("Not yet implemented");
    },

    async submitApplication(userInfo) {
        console.log("Not yet implemented");
    },

}

