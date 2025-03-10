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

  function validateForm() {
    const nameRegex = /^[A-Za-z\s]+$/; // Allows only letters and spaces
    error.value = ""; // Reset previous errors
    successMessage.value = ""; // Reset previous success messages

    if (!formData.value.candidateName.trim()) {
      error.value = "Full Name is required.";
      return false;
    }
    if (!nameRegex.test(formData.value.candidateName)) {
      error.value = "Full Name must contain only letters.";
      return false;
    }
    if (!formData.value.skills) {
      error.value = "Competence Profile must be selected.";
      return false;
    }
    if (!formData.value.experienceYears || formData.value.experienceYears < 0) {
      error.value = "Experience Years must be a non-negative number.";
      return false;
    }
    if (!formData.value.availableFrom) {
      error.value = "Available From date is required.";
      return false;
    }
    if (!formData.value.availableTo) {
      error.value = "Available To date is required.";
      return false;
    }
    if (new Date(formData.value.availableFrom) > new Date(formData.value.availableTo)) {
      error.value = "Available To date must be after Available From date.";
      return false;
    }
    return true;
  }

  async function handleSubmit() {
    if (isSubmitting.value) return; // Prevent duplicate submissions
    if (!validateForm()) return; // Stop if validation fails

    isSubmitting.value = true;
    error.value = null; // Reset previous errors
    successMessage.value = ""; // Reset previous success messages

    try {
      const response = await candidateApplicationModel.submitApplication(formData.value);

      // Reset form only if submission is successful
      formData.value = {
        candidateName: "",
        skills: "",
        experienceYears: "",
        availableFrom: "",
        availableTo: "",
      };

      // Fetch updated applications list
      await candidateApplicationModel.fetchApplications();

      successMessage.value = `Application submitted! ID: ${response.data.id}`;
    } catch (err) {
      if (err.response) {
        // Backend responded with an error (4xx or 5xx)
        console.error("Server error:", err.response.data);
        error.value = `Server error: ${err.response.data.message || "An error occurred on the server."}`;
      } else if (err.request) {
        // No response from server
        console.error("Network error:", err.request);
        error.value = "Network error: Could not connect to the server.";
      } else {
        // Unknown error in frontend
        console.error("Unexpected error:", err.message);
        error.value = `Unexpected error: ${err.message}`;
      }
    } finally {
      isSubmitting.value = false; // Ensure submitting state is reset
    }
  }

  function toggleForm() {
    showForm.value = !showForm.value;
  }

  onMounted(async () => {
    try {
      await candidateApplicationModel.fetchApplications();
    } catch (err) {
      console.error("Error fetching applications:", err);
      error.value = "Failed to fetch applications. Please try again.";
    }
  });

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
