import { advertisementService, candidateApplicationService } from "./services/resolvePromise.js";
import { ref, onMounted } from "vue";

export default {
    //Advertisement
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

    //CandidateApplication
    applications: [],
    applicationError: null,
    applicationLoading: false,

    //Advertisement functions with pagination
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

    // Go to next page
    async goToNextPage() {
        if (!this.advertisementPageInfo.last && !this.advertisementLoading) {
            this.advertisementPageInfo.pageNumber += 1;
            await this.fetchAdvertisements();
        }
    },

    // Go to previous page
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

    //CandidateApplication functions
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