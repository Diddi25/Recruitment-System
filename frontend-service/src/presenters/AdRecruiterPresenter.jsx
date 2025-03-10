import AdRecruiterView from "@/views/AdRecruiterView";

/**
 * Used by a recruiter to list all available applications.
 * @param {*} props The reactive model
 * @returns The recruiter presenter page.
 */
export default function AdRecruiterPresenter(props) {

    return <div class="main"><AdRecruiterView model={props.model} createAd={onAdCreate}
        deleteAd={onAdDelete} updateAd={onAdUpdate} updateAdStatus={onAdStatusUpdate} getAllAds={onFetchAds}/></div>;

    function onAdCreate(data) {
        props.model.createAdvertisement(data)
    }

    function onAdDelete(id) {
        props.model.deleteAdvertisement(id)
    }

    function onAdUpdate(id) {
        props.model.updateAdvertisement(id)
    }

    function onAdStatusUpdate(id, status) {
        props.model.updateAdvertisementStatus(id, status)
    }

    function onFetchAds() {
        props.model.fetchAdvertisements();
    }
    
}