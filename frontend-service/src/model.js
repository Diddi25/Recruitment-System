import { ref, onMounted } from 'vue'
import { advertisementService } from './services/api.js'

export default {
    advertisement: [],
    advertisementError: null,
    advertisementLoading: false,
    advertisementShowForm: false,
    formData: {
        advertisementText: '',
        assigned: '',
        status: 'unhandled'
    },

    async fetchAdvertisements () {
        this.advertisementLoading = true
        try {
          let result = await advertisementService.getAll()
          this.advertisement = result.data
        } catch (err) {
          this.advertisementError = `Error fetching advertisements: ${err.message}`;
        } finally {
          this.advertisementLoading = false;
        }
    },

    async handleCreate() {
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

    async handleDelete(id) {
        if (confirm('Are you sure you want to delete this advertisement?')) {
            try {
                await advertisementService.delete(id);
                await this.fetchAdvertisements();
            } catch (err) {
                this.advertisementError = `Error deleting advertisement: ${err.message}`;
            }
        }
    },

    async handleStatusUpdate(id, newStatus) {
        try {
            await advertisementService.updateStatus(id, { status: newStatus });
            await this.fetchAdvertisements();
        } catch (err) {
            this.advertisementError = `Error updating status: ${err.message}`;
        }
    },



}

