/*
* Is used to display a single application.
*/

import ApplicationView from "@/views/ApplicationView";

export default function ApplicationPresenter(props) {

  function handleSubmit(formData) {
    props.model.submitApplication(formData)
  }


        return (<div class="main">
                     <ApplicationView
                        onSubmit = {handleSubmit}
                        errorMsg = {props.model.errorMessages.applicationSubmission}
                     />
                   </div>
                 );
}
