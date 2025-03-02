import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {
    return <div class="main">
                <ApplicationListView model={props.model}
                                     listOfApplications={props.model.advertisement}
                                     fetchApplications={fetchApplicationsACB}
                /></div>;

                function fetchApplicationsACB() {
                    props.model.fetchAdvertisements();
                }

}
