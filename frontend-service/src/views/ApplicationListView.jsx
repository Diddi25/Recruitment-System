
import { onMounted } from 'vue' // it's never used in the code

/*export default function ApplicationListView(props) {
  
    function fetchAds() {
        props.fetchApplications();
  }

  function renderAdvertisement(ad) {
    return <tr key={ad.id} >
                <td>{ad.id}</td>
                <td>{ad.advertisementText}</td>
                <td>{ad.assigned}</td>
                <td>{ad.status}</td>
           </tr>;
  }
  return (<div> {
    <div>
        <div>
            <button type="button" onClick={fetchAds}>Click to fetch</button>
        </div>
        <div>
            <table>
                <tbody>
                  {
                    props.listOfApplications.map(renderAdvertisement)
                  }
                </tbody>
            </table>
        </div>
    </div>
} </div>);
} */
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


