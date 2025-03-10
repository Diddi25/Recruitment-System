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
}  */
export default function ApplicationListView(props) {
        return (
            <div>
                <h3>All Applications</h3>
                {props.applications && props.applications.length > 0 ? (
                    <ul>
                        {props.applications.map((app) => (
                            <li key={app.id}>
                                {app.candidateName} - {app.status || "No status available"}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No applications found.</p>
                )}
            </div>
        );
      } 