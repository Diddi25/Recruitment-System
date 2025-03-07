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
        <div><button onClick={fetchAds}>Get all ads</button></div>
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
            <h3>{ad.advertisementText}</h3><p>Status: {getStatusSelection(ad)}</p> <p>Assigned: {ad.assigned}</p>
            <button onClick={()=>onDelete(ad.id)}>Delete</button>
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

    function onDelete(id) {
        props.deleteAd(id);
        fetchAds();
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

    function getStatusSelection(ad) {
        return(
            <select name="status" value={ad.status} onChange={(input) => updateStatus(input, ad.id)}>
                <option value="unhandled">Unhandled</option>
                <option value="handled">Handled</option>
            </select>
        );
    }

    function updateStatus(input, id){
        props.updateAdStatus(id, input.target.value)
    }
}
