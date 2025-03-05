/*import ApplicationListView from "@/views/ApplicationListView";
export default function ApplicationListPresenter(props) {
  
  function fetchApplicationsACB() {
    props.model.fetchAdvertisements();
  }

  return (
    <div class="main">
      <ApplicationListView
        model={props.model}
        listOfApplications={props.model.advertisement}
        fetchApplications={fetchApplicationsACB}
      />
    </div>
  );
} */

import ApplicationListView from "@/views/ApplicationListView";
import candidateApplicationModel from "@/candidateApplicationModel";

export default function ApplicationListPresenter() {
  
  function fetchApplicationsACB() {
    candidateApplicationModel.fetchApplications();  
  }
  function closeApplications() {
    showApplications.value = false;  // Hide applications when closing, but its currently not used.
  }

  return (
    <div class="main">
      <button onClick={fetchApplicationsACB}>Fetch Applications</button> 
      <ApplicationListView applications={candidateApplicationModel.applications.value || []} />
      
    </div>
  );
}
  