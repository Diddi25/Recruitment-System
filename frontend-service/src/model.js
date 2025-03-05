import { advertisementService, candidateApplicationService} from "./services/resolvePromise.js";
import { ref, onMounted } from "vue";

const advertisement = ref([]);
const advertisementError = ref(null);
const advertisementLoading = ref(false);
const applications = ref([]);
const applicationError = ref(null);
const applicationLoading = ref(false);

export default {
    //Advertisement
    advertisement:null,
    advertisementError:null,
    advertisementLoading:null,
    formData: {
        advertisementText: '',
        assigned: '',
        status: 'unhandled'
    },

    //CandidateApplication
    applications:[],
    applicationError:null,
    applicationLoading:null,

    //Identification Service
    nameLogin: "Login",
    nameRegister: "Register",
    username: '',
    password: '',

    //Advertisement functions
    async fetchAdvertisements() {
      advertisementLoading.value = true;
      try {
        let result = await advertisementService.getAll();
        advertisement.value = result.data;
      } catch (err) {
        advertisementError.value = `Error fetching advertisements: ${err.message}`;
      } finally {
        advertisementLoading.value = false;
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
            fetchAdvertisements();
        } catch (error) {

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

    //CandidateApplication functions
    async fetchApplications() {
      applicationLoading.value = true;
      try {
        const response = await candidateApplicationService.getAllApplications();
        applications.value = response.data;
      } catch (err) {
        applicationError.value = `Error fetching applications: ${err.message}`;
      } finally {
        applicationLoading.value = false;
      }
    },

    async submitApplication(formData) {
      console.log("Sending application data to API:", formData); // Debugging log
      try {
        const response = await candidateApplicationService.applyForPosition(formData);
        return response;
      } catch (err) {
        throw new Error(`Error submitting application: ${err.message}`);
      }
    },

}

