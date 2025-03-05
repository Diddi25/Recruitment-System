/*import ApplicationView from "@/views/ApplicationView";
export default function ApplicationPresenter(props) {
    return <div class="main"><ApplicationView/></div>;
}*/

import { ref, onMounted } from "vue";
import ApplicationView from "@/views/ApplicationView";
import candidateApplicationModel from "@/candidateApplicationModel";

export default function ApplicationPresenter() {
  const showForm = ref(true);
  const successMessage = ref("");
  const error = ref(null);
  const isSubmitting = ref(false); // Prevent multiple submissions

  const formData = ref({
    candidateName: "",
    skills: "",
    experienceYears: "",
    availableFrom: "",
    availableTo: "",
  });

  async function handleSubmit() {
    if (isSubmitting.value) return; // Prevent duplicate submissions
    isSubmitting.value = true;

    try {
      const response = await candidateApplicationModel.submitApplication(formData.value);
      successMessage.value = `Application submitted! ID: ${response.data.id}`;
      error.value = null;

      // Reset form
      formData.value = {
        candidateName: "",
        skills: "",
        experienceYears: "",
        availableFrom: "",
        availableTo: "",
      };

      await candidateApplicationModel.fetchApplications();
    } catch (err) {
      error.value = `Error submitting application: ${err.message}`;
    } finally {
      isSubmitting.value = false; // Reset loading state
    }
  }

  function toggleForm() {
    showForm.value = !showForm.value;
  }

  onMounted(candidateApplicationModel.fetchApplications); // Fetch applications on mount

  return (
    <div class="main">
      <ApplicationView
        showForm={showForm.value}
        toggleForm={toggleForm}
        formData={formData}
        onSubmit={handleSubmit}
        applications={candidateApplicationModel.applications}
        error={error}
        successMessage={successMessage}
      />
    </div>
  );
}
