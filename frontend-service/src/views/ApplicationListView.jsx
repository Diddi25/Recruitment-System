/*export default function ApplicationListView(props) {
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
} */

    export default function ApplicationListView(props) {
        return (
            <div>
                <h2>Lista över ansökningar</h2>
                {props.model.applications.length > 0 ? (
                    props.model.applications.map(listApplications)
                ) : (
                    <p>Inga ansökningar att visa.</p>
                )}
            </div>
        );
    
        function listApplications(application) {
            return (
                <div className="applicationListElement" key={application.applicationId} onClick={() => onClickApplicant(application.applicationId)}>
                    {application.name + " " + application.lastName + ", status: " + application.status}
                </div>
            );
        }
    
        function onClickApplicant(applicationId) {
            props.viewApplicant(applicationId);
        }
    }
    