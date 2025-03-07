import { advertisementService, candidateApplicationService} from "./services/resolvePromise.js";
import { ref, onMounted } from "vue";

export default {
    //Advertisement
    advertisement: [],
    advertisementError: null,
    advertisementLoading: false,
    formData: {
        advertisementText: '',
        assigned: '',
        status: 'unhandled'
    },

    //CandidateApplication
    applications:[],
    applicationError: null,
    applicationLoading: false,

    //Advertisement functions
    async fetchAdvertisements() {
      this.advertisementLoading = true;
      try {
        let result = await advertisementService.getAll();
        this.advertisement = result.data;
      } catch (err) {
        this.advertisementError = `Error fetching advertisements: ${err.message}`;
      } finally {
        this.advertisementLoading = false;
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
      applicationLoading = true;
      try {
        const response = await candidateApplicationService.getAllApplications();
        this.applications = response.data;
      } catch (err) {
        this.applicationError = `Error fetching applications: ${err.message}`;
      } finally {
        this.applicationLoading = false;
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

