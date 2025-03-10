import { ref, onMounted } from "vue";
import { candidateApplicationService } from "./services/api.js";

const applications = ref([]);
const applicationError = ref(null);
const applicationLoading = ref(false);

async function fetchApplications() {
  applicationLoading.value = true;
  try {
    const response = await candidateApplicationService.getAllApplications();
    applications.value = response.data;
  } catch (err) {
    applicationError.value = `Error fetching applications: ${err.message}`;
  } finally {
    applicationLoading.value = false;
  }
}

async function submitApplication(formData) {
  console.log("Sending application data to API:", formData); // Debugging log
  try {
    const response = await candidateApplicationService.applyForPosition(formData);
    return response;
  } catch (err) {
    throw new Error(`Error submitting application: ${err.message}`);
  }
}

onMounted(fetchApplications);

export default {
  applications,
  applicationError,
  applicationLoading,
  fetchApplications,
  submitApplication,
};