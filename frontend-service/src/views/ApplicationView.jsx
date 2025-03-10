export default function ApplicationView(props) {

  let formData = {
    candidateName: "",
    skills: "",
    experienceYears: "",
    availableFrom: "",
    availableTo: "",
  };

 function handleSubmit(event) {
    event.preventDefault();
    document.getElementById('thanksMessage').style.display = 'block';
  }

  return (<div class="apply">
            <div>
              <h3>Fill in your details</h3>
              <form onSubmit={handleSubmit}>
    
                <div>
                  <label>Competence Profile:</label><br />
                  <select name="competence">
                      <option value="1">ticket sales</option>
                      <option value="2">lotteries</option>
                      <option value="3">roller coaster operation</option>
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
            <div id="thanksMessage" style="display: none; margin-top: 20px; color: green; font-weight: bold;">
              Your application is successful!
            </div>
          </div>);
}