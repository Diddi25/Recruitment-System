   /* import { ref, onMounted, defineComponent } from 'vue';
    import { candidateApplicationService } from '../services/api.js';
    
    export default defineComponent({
      setup() {
        const showForm = ref(true);
        const successMessage = ref('');
        const error = ref(null);
        const applications = ref([]); // Stores job applications
    
        // Form data
        const formData = ref({
          candidateName: '',
          competence: '',
          experienceYears: '',
          availableFrom: '',
          availableTo: ''
        });
    
        // Fetch applications on mount
        onMounted(async () => {
          try {
            const response = await candidateApplicationService.getAllApplications();
            applications.value = response.data;
          } catch (err) {
            error.value = `Error fetching applications: ${err.message}`;
          }
        });
    
        // Handle form submission
        const handleSubmit = async () => {
          try {
            const response = await candidateApplicationService.applyForPosition(formData.value);
            successMessage.value = `Application submitted! ID: ${response.data.id}`;
            error.value = null;
            formData.value = { candidateName: '', competence: '', experienceYears: '', availableFrom: '', availableTo: '' };
    
            // Refresh applications list after submitting
            const newResponse = await candidateApplicationService.getAllApplications();
            applications.value = newResponse.data;
          } catch (err) {
            error.value = `Error submitting application: ${err.message}`;
          }
        };
    
        return () => (
          <div class="apply">
            <h2>Register Job Application</h2>
    
            <button onClick={() => (showForm.value = !showForm.value)}>
              {showForm.value ? 'Cancel' : 'Apply for a Position'}
            </button>
    
            {showForm.value && (
              <div>
                <h3>Fill in your details</h3>
                <form onSubmit={(e) => { e.preventDefault(); handleSubmit(); }}>
                  <div>
                    <label>Full Name:</label><br />
                    <input type="text" v-model={formData.value.candidateName} required />
                  </div>
    
                  <div>
                    <label>Competence Profile:</label><br />
                    <input type="text" v-model={formData.value.competence} required />
                  </div>
    
                  <div>
                    <label>Experience Years:</label><br />
                    <input type="number" v-model={formData.value.experienceYears} min="0" required />
                  </div>
    
                  <div>
                    <label>Available From:</label><br />
                    <input type="date" v-model={formData.value.availableFrom} required />
                  </div>
    
                  <div>
                    <label>Available To:</label><br />
                    <input type="date" v-model={formData.value.availableTo} required />
                  </div>
    
                  <br />
                  <button type="submit">Submit</button>
                </form>
              </div>
            )}
    
            {error.value && <p><b>Error:</b> {error.value}</p>}
            {successMessage.value && <p><b>{successMessage.value}</b></p>}
    
            <h3>All Applications</h3>
            {applications.value.length > 0 ? (
              <ul>
                {applications.value.map((app) => (
                  <li key={app.id}>{app.candidateName} - {app.status}</li>
                ))}
              </ul>
            ) : (
              <p>No applications found.</p>
            )}
          </div>
        );
      }
    });

    */

    export default function ApplicationView(props) {
      return (
        <div class="apply">
          <h2>Register Job Application</h2>
    
          <button onClick={props.toggleForm}>
            {props.showForm ? "Cancel" : "Apply for a Position"}
          </button>
    
          {/* Job Application Form */}
          {props.showForm && (
            <div>
              <h3>Fill in your details</h3>
              <form onSubmit={(e) => { e.preventDefault(); props.onSubmit(); }}>
                <div>
                  <label>Full Name:</label><br />
                  <input type="text" v-model={props.formData.value.candidateName} required />
                </div>
    
                <div>
                  <label>Competence Profile:</label><br />
                  <input type="text" v-model={props.formData.value.skills} required />
                </div>
    
                <div>
                  <label>Experience Years:</label><br />
                  <input type="number" v-model={props.formData.value.experienceYears} min="0" required />
                </div>
    
                <div>
                  <label>Available From:</label><br />
                  <input type="date" v-model={props.formData.value.availableFrom} required />
                </div>
    
                <div>
                  <label>Available To:</label><br />
                  <input type="date" v-model={props.formData.value.availableTo} required />
                </div>
    
                <br />
                <button type="submit">Submit</button>
              </form>
            </div>
          )}

          {/* Error & Success Messages */}
          {props.error.value && <p><b>Error:</b> {props.error.value}</p>}
          {props.successMessage.value && <p><b>{props.successMessage.value}</b></p>}
    

        </div>
      );
    }
    