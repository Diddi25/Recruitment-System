import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {

    function fetchApplicationsACB() {
        props.model.fetchAdvertisements();
    }

      function closeApplications() {
        showApplications.value = false;  // Hide applications when closing, but its currently not used.
      }

    return (<div class="main">

                <ApplicationListView model={props.model}
                                     listOfApplications={props.model.advertisement}
                                     fetchApplications={fetchApplicationsACB}
           /></div>);
}
