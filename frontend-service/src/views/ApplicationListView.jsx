import { onMounted } from 'vue'

export default function ApplicationListView(props) {

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

}