import {login} from "./springRequests"
import { advertisementService, identificationService } from "./services/api";
export default {
    user: {username: null, password: null, isLoggedIn: null},
    flags: {incorrectLoginCredentials: false, usernameAlreadyExists: null},

    //Test applications
    applications: [{name: "Anna", lastName: "Andersson", status: "unhandled", applicationId: 1}, 
        {name: "Bertil", lastName: "Bengtsson", status: "unhandled", applicationId: 2}],

    async submitLoginCredentials(user, pass) {
        this.flags.incorrectLoginCredentials = null;
        const data = {username: user, password: pass}
        let result = await identificationService.login(data)
        this.user.isLoggedIn = result.success;
    },

    async submitRegistrationInfo(userInfo) {
        this.flags.usernameAlreadyExists = null;
        const data = {username: userInfo.username, password: userInfo.password, email: userInfo.email, role: ["recruiter"],}
        let result = await identificationService.register(data)

    },

    async submitApplication(userInfo) {
        console.log("Not yet implemented");
    },

}

