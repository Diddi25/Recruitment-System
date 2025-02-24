export default function AdRecruiterView(props) {

  let ads = props.model.advertisements;
  let currentText = null;
  let currentStatus = null;
  let currentAssigned = null;
  
  if(ads) {
    return (<div>
      {
        getAdSubmissionForm()
      }
      <h2>Published ads</h2>
      {
        ads.map(listAds)
      }
    </div>);
  }
  else {
    return (<div>
      {
      getAdSubmissionForm()
      }
      <button onClick={fetchAds}>Get all ads</button></div>);
  }

  function fetchAds(){
    props.getAllAds();
  }

  function listAds(ad) {
    return (<div>
        <h3>{ad.advertisementText}</h3><p>Status: {ad.status}</p> <p>Assigned: {ad.assigned}</p>
    </div>);
  }

  function getAdSubmissionForm() {
    return (        
      <div><h2>Create an advertisement</h2>
      <h4>Description</h4>
      <div class="text-input"><input type="text" onInput={onTextInput}>
      </input></div>
      <h4>Status</h4>
      <div class="text-input"><input type="text" onInput={onStatusInput}></input></div>
      <h4>Assigned</h4>
      <div class="text-input"><input type="text" onInput={onAssignedInput}></input></div>
      <button onClick={onSubmit}>Submit</button></div>
    );
  }

  function onAssignedInput(input) {
    currentAssigned = input.target.value;
  }

  function onTextInput(input) {
    currentText = input.target.value;
  }

  function onStatusInput(input) {
    currentStatus = input.target.value;
  }

  function onSubmit() {
    props.createAd({advertisementText: currentText, status: currentStatus, assigned:currentAssigned});
  }
}
