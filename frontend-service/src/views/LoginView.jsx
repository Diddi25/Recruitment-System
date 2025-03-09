
export default function LoginView(props) {

    let username = "";
    let password = "";

    return (<div class="login">
        <h2>Log in</h2>
        <h4>Username</h4>
        <div class="text-input"><input type="text" onInput={onUsernameInput}>
        </input></div>
        <h4>Password</h4>
        <div class="text-input"><input type="text" onInput={onPasswordInput}></input></div>
        {getIncorrectCredentialsWarning()}
        <button onClick={onSubmit}>Submit</button>
    </div>);

    function onUsernameInput(input){
        username = input.target.value;
    }

    function onPasswordInput(input){
        password = input.target.value;
    }

    function getIncorrectCredentialsWarning(){
        if(props.model.errorMessages.loginSubmission) {
            return (<div class="submissionErrorMsg">{props.model.errorMessages.loginSubmission}</div>);
        }
    }

    function isUsernameValid(username) {
        if(username.length > 20 || username.length < 3) {
            return false;
        }
        return true;
    }

    function isPasswordValid(password) {
        if(password.length > 256 || password.length < 6) {
            return false;
        }
        return true;
    }

    function onSubmit() {
        if(!isUsernameValid(username)) {
            props.setUsernameValidationError(true);
            return;
        }

        if(!isPasswordValid(password)) {
            props.setPasswordValidationError(true);
            return;
        }
        props.submitLoginCredentials(username, password);
    }
}