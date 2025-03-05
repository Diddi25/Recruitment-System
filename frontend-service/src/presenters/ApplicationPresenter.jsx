/*
* Is used to display a single application.
*/

import ApplicationView from "@/views/ApplicationView";
export default function ApplicationPresenter(props) {
    if(props.model.user.role == "user") {
        return <div class="main"><ApplicationView/></div>;
    }
    else if(props.model.user.role == "recruiter") {
        return <h2>This page is not available for recruiters.</h2>;
    }
    else {
        return <h2>Only authenticated users are able to apply.</h2>;
    }
}