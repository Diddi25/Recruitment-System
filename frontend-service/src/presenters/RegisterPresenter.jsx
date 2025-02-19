import RegisterView from "@/views/RegisterView";
export default function RegisterPresenter(props) {
    return <div class="main"><RegisterView model={props.model} submitRegistrationInfo={onSubmitRegistrationInfo}/></div>;

    function onSubmitRegistrationInfo(userInfo) {
        props.model.submitRegistrationInfo(userInfo);
    }
}