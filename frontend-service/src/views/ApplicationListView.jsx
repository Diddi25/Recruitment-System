
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