
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
        else {
            return <div></div>;
        }
    }

    function onSubmit() {
        props.submitLoginCredentials(username, password);
    }
}