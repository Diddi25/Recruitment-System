/*import ApplicationView from "@/views/ApplicationView";
export default function ApplicationPresenter(props) {
    return <div class="main"><ApplicationView/></div>;
}*/


import { ref, onMounted, defineComponent } from "vue";
import ApplicationView from "@/views/ApplicationView";
import { candidateApplicationService } from "../services/api.js";

export default defineComponent({
  setup() {
    const applications = ref([]);
    const error = ref(null);
    const loading = ref(false);
    const successMessage = ref("");

    // Form data
    const formData = ref({
      candidateName: "",
      competence: "",
      experienceYears: "",
      availableFrom: "",
      availableTo: "",
    });

    // Fetch applications on mount
    onMounted(async () => {
      fetchApplications();
    });

    const fetchApplications = async () => {
      loading.value = true;
      try {
        const response = await candidateApplicationService.getAll();
        applications.value = response.data;
      } catch (err) {
        error.value = `Error fetching applications: ${err.message}`;
      } finally {
        loading.value = false;
      }
    };

    // Handle form submission
    const handleSubmit = async () => {
      try {
        const response = await candidateApplicationService.apply(formData.value);
        successMessage.value = `Application submitted! ID: ${response.data.id}`;
        error.value = null;
        formData.value = { candidateName: "", competence: "", experienceYears: "", availableFrom: "", availableTo: "" };

        // Refresh applications list after submitting
        fetchApplications();
      } catch (err) {
        error.value = `Error submitting application: ${err.message}`;
      }
    };

    return () => (
      <div class="main">
        <ApplicationView
          applications={applications.value}
          formData={formData.value}
          handleSubmit={handleSubmit}
          loading={loading.value}
          error={error.value}
          successMessage={successMessage.value}
        />
      </div>
    );
  }
});
