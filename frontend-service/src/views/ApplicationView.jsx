/**
 * Enables the user to submit an application
 * @param {*} props 
 * @returns 
 */
export default function ApplicationView(props) {

  let formData = {
    candidateName: "",
    skills: "",
    experienceYears: "",
    availableFrom: "",
    availableTo: "",
  };

  function getSubmissionErrorMsg(){
      if(props.errorMsg != null) {
          return (<div class="submissionErrorMsg">{props.errorMsg}</div>);
      }
      else return(<div></div>);
  }

  return (<div class="apply">
            <div>
              <h3>Fill in your details</h3>
              <form onSubmit={(e) => {e.preventDefault();
                props.onSubmit(formData);
                }}>
                <div>
                  <label>Full Name:</label><br />
                  <input type="text" v-model={formData.candidateName} required />
                </div>
                
                <div>
                <label>Competence Profile:</label><br />
                <select v-model={formData.skills} required>
                <option value="">Select a position</option>
                {props.competenceOptions.map((comp) => (
                  <option key={comp.id} value={comp.id}>{comp.name}</option>
                ))}
                </select>
                </div>
                <div>
                  <label>Experience Years:</label><br />
                  <input type="number" v-model={formData.experienceYears} min="0" required />
                </div>
    
                <div>
                  <label>Available From:</label><br />
                  <input type="date" v-model={formData.availableFrom} required />
                </div>
    
                <div>
                  <label>Available To:</label><br />
                  <input type="date" v-model={formData.availableTo} required />
                </div>
    
                <br />
                <button type="submit">Submit</button>
              </form>
            </div>
            {
              getSubmissionErrorMsg()
            }
          </div>);
}
