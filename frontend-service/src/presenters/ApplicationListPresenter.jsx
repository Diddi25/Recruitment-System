import ApplicationListView from "@/views/ApplicationListView";

/*export default function ApplicationListPresenter(props) {
    return <div className="main"><ApplicationListView model={props.model} viewApplicant={onViewApplicant}/></div>;

    function onViewApplicant(){

    }
}*/

import { ref, onMounted, defineComponent } from "vue";
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
      <div className="main">
        <ApplicationListView model={{ applications: this.applications }} viewApplicant={this.onViewApplicant} />
      </div>
    );
  },
});
