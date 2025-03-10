/**
 * Lists all applications
 * @param {*} props 
 * @returns An interface displaying all candidate applications
 */

export default function ApplicationListView(props) {
    const {
        listOfApplications,
        fetchApplications,
        pageInfo,
        goToNextPage,
        goToPreviousPage,
        isLoading
    } = props;

    return (
        <div>
            <h3>All Applications</h3>
            <button onClick={fetchApplications} disabled={isLoading}>
                {isLoading ? 'Loading...' : 'Fetch Applications'}
            </button>

            {listOfApplications && listOfApplications.length > 0 ? (
                <div>
                    <div className="pagination-info">
                        Showing {pageInfo.pageNumber * pageInfo.pageSize + 1} -
                        {Math.min((pageInfo.pageNumber + 1) * pageInfo.pageSize, pageInfo.totalElements)} of {pageInfo.totalElements} applications
                    </div>

                    <ul className="applications-list">
                        {listOfApplications.map((app) => (
                            <li key={app.id} className="application-item">
                                <div className="application-header">
                                    <strong>{app.personName} {app.personSurname}</strong> - {app.competenceName}
                                </div>
                                <div className="application-details">
                                    <div>Experience: {app.yearsOfExperience} years</div>
                                    <div>Status: {app.status || "Unhandled"}</div>
                                    <div>Email: {app.personEmail}</div>
                                </div>
                            </li>
                        ))}
                    </ul>

                    <div className="pagination-controls">
                        <button
                            onClick={goToPreviousPage}
                            disabled={pageInfo.pageNumber === 0 || isLoading}
                        >
                            Previous Page
                        </button>
                        <span className="page-indicator">
                            Page {pageInfo.pageNumber + 1} of {pageInfo.totalPages}
                        </span>
                        <button
                            onClick={goToNextPage}
                            disabled={pageInfo.last || isLoading}
                        >
                            Next Page
                        </button>
                    </div>
                </div>
            ) : (
                <p>No applications found.</p>
            )}
        </div>
    );
}
