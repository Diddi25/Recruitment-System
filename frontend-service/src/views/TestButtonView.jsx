// src/views/TestButtonView.jsx
import { ref, onMounted } from 'vue'
import { advertisementService } from '../services/api'

export default {
  setup() {
    const advertisements = ref([])
    const error = ref(null)
    const loading = ref(false)
    const showForm = ref(false)

    const formData = ref({
      advertisementText: '',
      assigned: '',
      status: 'unhandled'
    })

    // Hämta alla annonser vid mount
    onMounted(() => {
      fetchAdvertisements()
    })

    const fetchAdvertisements = async () => {
      loading.value = true
      try {
        const result = await advertisementService.getAll()
        advertisements.value = result.data
      } catch (err) {
        error.value = `Error fetching advertisements: ${err.message}`
      } finally {
        loading.value = false
      }
    }

    const handleCreate = async () => {
      try {
        await advertisementService.create(formData.value)
        fetchAdvertisements()
        showForm.value = false
        formData.value = { advertisementText: '', assigned: '', status: 'unhandled' }
      } catch (err) {
        error.value = `Error creating advertisement: ${err.message}`
      }
    }

    const handleUpdate = async (id) => {
      try {
        const adToUpdate = advertisements.value.find(ad => ad.id === id)
        if (!adToUpdate) return
        await advertisementService.update(id, adToUpdate)
        fetchAdvertisements()
      } catch (err) {
        error.value = `Error updating advertisement: ${err.message}`
      }
    }

    const handleDelete = async (id) => {
      if (confirm('Are you sure you want to delete this advertisement?')) {
        try {
          await advertisementService.delete(id)
          fetchAdvertisements()
        } catch (err) {
          error.value = `Error deleting advertisement: ${err.message}`
        }
      }
    }

    const handleStatusUpdate = async (id, newStatus) => {
      try {
        await advertisementService.updateStatus(id, { status: newStatus })
        fetchAdvertisements()
      } catch (err) {
        error.value = `Error updating status: ${err.message}`
      }
    }

    return {
      advertisements,
      error,
      loading,
      showForm,
      formData,
      fetchAdvertisements,
      handleCreate,
      handleUpdate,
      handleDelete,
      handleStatusUpdate
    }
  },

  template: `
    <div>
      <h2>Advertisement Management</h2>

      <button @click="showForm = !showForm">
        {{ showForm ? 'Cancel' : 'Create New Advertisement' }}
      </button>

      <div v-if="showForm">
        <h3>Create New Advertisement</h3>
        <form @submit.prevent="handleCreate">
          <div>
            <label>Advertisement Text:</label>
            <br />
            <input type="text" v-model="formData.advertisementText" required />
          </div>
          <br />
          <div>
            <label>Assigned To:</label>
            <br />
            <input type="text" v-model="formData.assigned" required />
          </div>
          <br />
          <div>
            <label>Status:</label>
            <br />
            <select v-model="formData.status">
              <option value="unhandled">Unhandled</option>
              <option value="accepted">Accepted</option>
              <option value="rejected">Rejected</option>
            </select>
          </div>
          <br />
          <button type="submit">Create</button>
        </form>
      </div>

      <p v-if="error"><b>Error:</b> {{ error }}</p>
      <p v-if="loading">Loading...</p>

      <h3>Advertisements</h3>
      <div v-for="ad in advertisements" :key="ad.id">
        <hr />
        <p><b>ID:</b> {{ ad.id }}</p>
        <p><b>Text:</b> {{ ad.advertisementText }}</p>
        <p><b>Assigned:</b> {{ ad.assigned }}</p>
        <p>
          <b>Status:</b>
          <select v-model="ad.status" @change="handleStatusUpdate(ad.id, ad.status)">
            <option value="unhandled">Unhandled</option>
            <option value="accepted">Accepted</option>
            <option value="rejected">Rejected</option>
          </select>
        </p>
        <button @click="handleUpdate(ad.id)">Update</button>
        <button @click="handleDelete(ad.id)">Delete</button>
      </div>
    </div>
  `
}
