
import RegisterView from "@/views/RegisterView";

/**
 * Enables the user to register an account.
 * @param {*} props The reactive model
 * @returns A registration presenter.
 */
export default function RegisterPresenter(props) {
    return <div class="main"><RegisterView model={props.model} submitRegistrationInfo={onSubmitRegistrationInfo}/></div>;

    function onSubmitRegistrationInfo(userInfo) {
        props.model.submitRegistrationInfo(userInfo);
    }
}