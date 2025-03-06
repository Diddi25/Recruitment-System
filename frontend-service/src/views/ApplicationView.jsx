
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
    