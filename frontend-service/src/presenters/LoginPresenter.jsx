import LoginView from "@/views/LoginView";
import LoginSuccessView from "@/views/LoginSuccessView";

export default function LoginPresenter(props) {
    if(props.model.user.isLoggedIn != true) {
        return <div class="main"><LoginView model={props} submitLoginCredentials={onSubmitLoginCredentials}/></div>;
    }
    else {
        return <div class="main"><LoginSuccessView/></div>
    }

    function onSubmitLoginCredentials(username, password) {
        props.model.submitLoginCredentials(username, password)
    }
}