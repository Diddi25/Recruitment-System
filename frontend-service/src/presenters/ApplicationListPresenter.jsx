//import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {
    const showApplications = ref(true);

    function onViewApplicant(){

    }
}*/

/*import { ref, onMounted, defineComponent } from "vue";
import { candidateApplicationService } from "../services/api.js";

export default defineComponent({
  setup() {
    const applications = ref([
        { applicationId: 1, name: "Alice", lastName: "Andersson", status: "accepted" },
        { applicationId: 2, name: "Bob", lastName: "Berg", status: "pending" },
      ]);
      
    // Fetch applications from API
    const fetchApplications = async () => {
      try {
        const response = await candidateApplicationService.getAllApplications();
        applications.value = response.data; // Store applications
        console.log("Fetched applications:", applications.value);
      } catch (error) {
        console.error("Error fetching applications:", error);
      }
    };

    const onViewApplicant = (applicationId) => {
      console.log("Clicked on applicant:", applicationId);
    };

    onMounted(fetchApplications);

    return {
      applications,
      onViewApplicant,
    };
  },
  render() {
    return (
        <div class="main">
            <ApplicationListView
                model={props.model}
                listOfApplications={props.model.advertisementContent}
                pageInfo={props.model.advertisementPageInfo}
                fetchApplications={fetchApplicationsACB}
                goToNextPage={goToNextPageACB}
                goToPreviousPage={goToPreviousPageACB}
                isLoading={props.model.advertisementLoading}
            />
        </div>
    );
  },
}); */


import ApplicationListView from "@/views/ApplicationListView"; // Keep this one
import candidateApplicationModel from "@/candidateApplicationModel";

export default function ApplicationListPresenter() {
  
  function fetchApplicationsACB() {
    candidateApplicationModel.fetchApplications();  
  }
  function closeApplications() {
    //showApplications.value = false;  // Hide applications when closing, but its currently not used.
  }

return (
    <div className="main">
      <button onClick={fetchApplicationsACB}>Fetch Applications</button> 
      <ApplicationListView applications={candidateApplicationModel.applications.value || []} />
    </div>
  );
}