/*
* Is used to enable the user to login to the app.
*/

import LoginView from "@/views/LoginView";
import LoginSuccessView from "@/views/LoginSuccessView";

export default function LoginPresenter(props) {
    if(props.model.user.isLoggedIn != true) {
        return <div class="main"><LoginView model={props.model}
                                            submitLoginCredentials={onSubmitLoginCredentials}
                                            setUsernameValidationError={onInvalidUsername}
                                            setPasswordValidationError={onInvalidPassword}/>
                </div>;
    }
    else {
        return <div class="main"><LoginSuccessView/></div>
    }

    function onSubmitLoginCredentials(username, password) {
        props.model.submitLoginCredentials(username, password)
    }

    function onInvalidUsername(val) {
        props.model.loginUsernameValidationError(val)
    }

    function onInvalidPassword(val) {
        props.model.loginPasswordValidationError(val)
    }
}