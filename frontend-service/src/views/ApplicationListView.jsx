
export default function ApplicationListView(props) {

    function fetchAdvertisements() {
        props.fetchApplications();
    }
  return (
      <div>
          <h3>All Applications</h3>
          <button onClick={fetchAdvertisements}>Fetch Applications</button>
          {props.listOfApplications && props.listOfApplications.length > 0 ? (
              <ul>
                  {props.listOfApplications.map((app) => (
                      <li key={app.id}>
                          {app.assigned} - {app.status || "No status available"}
                      </li>
                  ))}
              </ul>
          ) : (
              <p>No applications found.</p>
          )}
      </div>
  );
}