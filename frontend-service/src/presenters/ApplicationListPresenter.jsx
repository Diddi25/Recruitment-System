import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {

    function fetchApplicationsACB() {
        props.model.fetchAdvertisements();
    }

    return <div class="main">
                <ApplicationListView model={props.model}
                                     listOfApplications={props.model.advertisement}
                                     fetchApplications={fetchApplicationsACB}
           /></div>;
}
