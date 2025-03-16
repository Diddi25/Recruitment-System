import { advertisementService, candidateApplicationService } from "./services/resolvePromise.js";
import store from './store/storeIndex.js';

/**
 * The model represents the current state of the application.
 */
export default {
    
    user: {username: null, password: null, isLoggedIn: null, role: null, id: null}, //user information used to render Views
    flags: {incorrectLoginCredentials: false, usernameAlreadyExists: null}, //input flags
    errorMessages: {registerSubmission: null, loginSubmission: null, applicationSubmission: null}, //contains all possible error messages

    advertisementContent: [], // Actual list of advertisements (from content array)
    advertisementPageInfo: {  // Pagination metadata
        pageNumber: 0,
        pageSize: 100,
        totalElements: 0,
        totalPages: 0,
        last: true
    },
    advertisementError: null,
    advertisementLoading: false,
    formData: {
        advertisementText: '',
        assigned: '',
        status: 'unhandled'
    },

    competenceOptions: [
        { id: 1, name: "Ticket Sales" },
        { id: 2, name: "Lotteries" },
        { id: 3, name: "Roller Coaster Operation" },
      ],

    applications: [], //Actual list containing all applications
    applicationError: null, //application error message

    /**
     * Fetches all advertisements
     */
    async fetchAdvertisements() {
        this.advertisementLoading = true;
        try {
            // Use paged endpoint with current page number
            let result = await advertisementService.getPaged(this.advertisementPageInfo.pageNumber);

            // Update both content and pagination information
            this.advertisementContent = result.data.content;
            this.advertisementPageInfo = {
                pageNumber: result.data.pageNumber,
                pageSize: result.data.pageSize,
                totalElements: result.data.totalElements,
                totalPages: result.data.totalPages,
                last: result.data.last
            };
        } catch (err) {
            this.advertisementError = `Error fetching advertisements: ${err.message}`;
        } finally {
            this.advertisementLoading = false;
        }
    },

    /**
     * Displays the next page of applications
     */
    async goToNextPage() {
        if (!this.advertisementPageInfo.last && !this.advertisementLoading) {
            this.advertisementPageInfo.pageNumber += 1;
            await this.fetchAdvertisements();
        }
    },

    /**
     * Displays the previous page of applications
     */
    async goToPreviousPage() {
        if (this.advertisementPageInfo.pageNumber > 0 && !this.advertisementLoading) {
            this.advertisementPageInfo.pageNumber -= 1;
            await this.fetchAdvertisements();
        }
    },

    /**
     * Creates a new advertisement containing the form data.
     */
    async createAdvertisement(formData) {
        try {
            await advertisementService.create(this.formData);
            await this.fetchAdvertisements();
            this.advertisementShowForm = false;
            this.formData.advertisementText = '';
            this.formData.assigned = '';
            this.formData.status = 'unhandled';
        } catch (err) {
            this.advertisementError = `Error creating advertisement: ${err.message}`;
        }
    },

    /**
     * Used to update the content of a specific advertisement.
     * @param {*} id
     */
    async updateAdvertisement(id, adToUpdate) {
        try {
            await advertisementService.update(id, adToUpdate);
            this.fetchAdvertisements();
        } catch (error) {
            this.advertisementError = `Error updating advertisement: ${error.message}`;
        }
    },

    /**
     * Used to delete a specific advertisement.
     * @param {*} id
     */
    async deleteAdvertisement(id) {
        if (confirm('Are you sure you want to delete this advertisement?')) {
            try {
                await advertisementService.delete(id);
                await this.fetchAdvertisements();
            } catch (err) {
                this.advertisementError = `Error deleting advertisement: ${err.message}`;
            }
        }
    },

    /**
     * Used to update the status of an advertisement.
     * @param {*} id
     * @param {*} newStatus
     */
    async updateAdvertisementStatus(id, newStatus) {
        try {
            await advertisementService.updateStatus(id, { status: newStatus });
            await this.fetchAdvertisements();
        } catch (err) {
            this.advertisementError = `Error updating status: ${err.message}`;
        }
    },

    /**
     * Fetches all applications from the application service
     */
    async fetchApplications() {
        this.applicationLoading = true;
        try {
            const response = await candidateApplicationService.getAllApplications();
            this.applications = response.data;
        } catch (err) {
            this.applicationError = `Error fetching applications: ${err.message}`;
        } finally {
            this.applicationLoading = false;
        }
    },

    /**
     * Submits an application to the application service
     * @param {*} formData 
     * @returns 
     */
    async submitApplication(formData) {
        formData.id = this.user.id;
        console.log(formData);
        try {
            const response = await candidateApplicationService.applyForPosition(formData);
        } catch (error) {
            this.errorMessages.applicationSubmission = error.response?.data?.message || error.message || error.toString();
            return;
        }
        this.errorMessages.applicationSubmission = "Successfully submitted application";
    },

        
    /**
     * Used to register a user.
     * Submits the user-provided registration information to the identification service 
     * and fetches the result.
     * @param {*} userInfo 
    */
    async submitRegistrationInfo(userInfo) {
        let data = userInfo;
        let result = "";
        try {
            result = await store.dispatch("auth/register", userInfo);
        }
        catch (error) {
            this.errorMessages.registerSubmission = error.response?.data?.message || error.message || error.toString();
            return;
        }
        this.errorMessages.registerSubmission = "User registration was successfull";
    },
    

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
            result = await store.dispatch("auth/login", data);
        }
        catch (error) {
            this.errorMessages.loginSubmission = error.response?.data?.message || error.message || error.toString();
            return;
        }
        let usr = JSON.parse(localStorage.getItem('user'));
        console.log(usr);
        if(usr.roles[0]) {
            this.user.role = usr.roles[0];
        }
        if(usr.id) {
            this.user.id = usr.id;
        }
        this.user.isLoggedIn = true;
    },

    /**
     * Sets the error message for the username input validation.
     */
      loginUsernameValidationError(val) {
        if (val == true) {
            this.errorMessages.loginSubmission = "Invalid username. The username must be between 3 and 20 characters long.";
        }
        else {
            this.errorMessages.loginSubmission = null;
        }
    },

    /**
     * Sets the error message for the password input validation.
     */
    loginPasswordValidationError(val) {
        if(val == true) {
            this.errorMessages.loginSubmission = "Invalid password. The password must be between 6 and 256 characters long.";
        }
        else {
            this.errorMessages.loginSubmission = null;
        }
    },


    /**
     * Logs out the user
     */
    async logoutUser() {
        if(this.user.isLoggedIn) {
            try {
                await store.dispatch("auth/logout");
            } catch (error) {

            }
        }
        this.user.isLoggedIn = false;
    }
}