import { ref } from 'vue';
import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {
    const showApplications = ref(true);

    function fetchApplicationsACB() {
        props.model.fetchAdvertisements();
    }

    function goToNextPageACB() {
        props.model.goToNextPage();
    }

    function goToPreviousPageACB() {
        props.model.goToPreviousPage();
    }

    function closeApplications() {
        showApplications.value = false;  // Hide applications when closing, but its currently not used.
    }

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
}