import ApplicationListView from "@/views/ApplicationListView";

/**
 * Lists all available applications.
 * @param {*} props The reactive model.
 * @returns an Application List Presenter.
 */
export default function ApplicationListPresenter(props) {

    function fetchApplicationsACB() {
        props.model.fetchAdvertisements();
    }

    function goToNextPageACB() {
        props.model.goToNextPage();
    }

    function goToPreviousPageACB() {
        props.model.goToPreviousPage();
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