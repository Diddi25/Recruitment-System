import { advertisementService, identificationService } from "./services/api";
export default {
    user: {username: null, password: null, isLoggedIn: null},
    flags: {incorrectLoginCredentials: false, usernameAlreadyExists: null},
    errorMessages: {registerSubmission: null, loginSubmission: null},
    jwtToken: null,
    advertisements: null,

    //Test applications
    applications: [{name: "Anna", lastName: "Andersson", status: "unhandled", applicationId: 1}, 
        {name: "Bertil", lastName: "Bengtsson", status: "unhandled", applicationId: 2}],

    /**
     * Used to authenticate a user.
     * Submits the user-provided login information to the identification service
     * logging in the user, or displays an error message.
     * @param {string} user Username
     * @param {string} pass Password
     */
    async submitLoginCredentials(user, pass) {
        this.flags.incorrectLoginCredentials = null;
        const data = {username: user, password: pass}
        let result = null;
        try {
            result = await identificationService.login(data);
        }
        catch (error) {
            this.errorMessages.loginSubmission = error.response.data.message;
            return;
        }
        this.errorMessages.loginSubmission = "Successfully logged in"; 
        this.jwtToken = result.data.accessToken;
    },

    /**
     * Used to register a user.
     * Submits the user-provided registration information to the identification service 
     * and fetches the result.
     * @param {*} userInfo 
     */
    async submitRegistrationInfo(userInfo) {
        const data = {username: userInfo.username, password: userInfo.password, email: userInfo.email, role: ["recruiter"],}
        let result = "";
        try {
            result = await identificationService.register(data);
        }
        catch (error) {
            this.errorMessages.registerSubmission = error.response.data.message;
            return;
        }
        this.errorMessages.registerSubmission = result.data.message; 
    },

    /**
     * Used to submit an application
     * Submits the user-provided application info the the application service
     * and fetches the result.
     * @param {*} userInfo 
     */
    async submitApplication(userInfo) {
        console.log("Not yet implemented");
    },

    /**
     * Fetches all advertisements from the advertisement service.
     */
    async fetchAdvertisements() {
        let result = null;
        try {
            result = await advertisementService.getAll();
            this.advertisements = result.data;
        } catch (error) {

        } 
    },

    /**
     * Creates a new advertisement containing the form data.
     */
    async createAdvertisement(formData) {
        try {
            await advertisementService.create(formData);
            fetchAdvertisements();

        } catch (error) {

        }    
    },

    /**
     * Used to update the content of a specific advertisement.
     * @param {*} id 
     */
    async updateAdvertisement(id, adToUpdate) {
        try {
            await advertisementService.update(id, adToUpdate);
            fetchAdvertisements();
        } catch (error) {

        }    
    },

    /**
     * Used to delete a specific advertisement.
     * @param {*} id 
     */
    async deleteAdvertisement(id) {
        let result = await advertisementService.delete(id);
        this.fetchAdvertisements();
    },

    /**
     * Used to update the status of an advertisement.
     * @param {*} id 
     * @param {*} newStatus 
     */
    async updateAdvertisementStatus(id, newStatus) {
        await advertisementService.updateStatus(id, newStatus);
    },

}

