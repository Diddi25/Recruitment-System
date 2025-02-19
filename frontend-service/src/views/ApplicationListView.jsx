export default function ApplicationListView(props) {
    return (<div>{
            props.model.applications.map(listApplications)
        }</div>);

    function listApplications(application){
        return(<div class="applicationListElement" onClick={onClickApplicant(application.applicationId)}>{application.name + " " + application.lastName + 
            ", status: " + application.status}</div>);
    }

    function onClickApplicant(applicationId) {
        props.viewApplicant(applicationId);
    }
}