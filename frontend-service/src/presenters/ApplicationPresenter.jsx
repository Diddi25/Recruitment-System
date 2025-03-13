import ApplicationView from "@/views/ApplicationView";

/**
 * Used to submit a single application.
 * @param {*} props The reactive model.
 * @returns An Application Presenter.
 */
export default function ApplicationPresenter(props) {

  function handleSubmit(formData) {
    props.model.submitApplication(formData)
  }

  return (
    <div class="main">
      <ApplicationView
        competenceOptions = {props.model.competenceOptions}
        onSubmit = {handleSubmit}
        errorMsg = {props.model.errorMessages.applicationSubmission}
      />
    </div>
  );
}
