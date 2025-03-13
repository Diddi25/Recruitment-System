import ApplicationListView from "@/views/ApplicationListView"; // Keep this one
//import candidateApplicationModel from "@/candidateApplicationModel"; // Remove this one?

export default function ApplicationListPresenter() {
  
  function fetchApplicationsACB() {
    candidateApplicationModel.fetchApplications();  
  }

return (
    <div className="main">
      <button onClick={fetchApplicationsACB}>Fetch Applications</button> 
      <ApplicationListView applications={candidateApplicationModel.applications.value || []} />
    </div>
  );
}