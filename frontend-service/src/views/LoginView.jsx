
/**
 * Enables the user to login using a submission form
 * @param {*} props 
 * @returns A login submission form.
 */
export default function LoginView(props) {

    let username = "";
    let password = "";

    return (<div class="login">
        <h2>Log in</h2>
        <h4>Username</h4>
        <div class="text-input"><input v-model={username} type="text" onInput={onUsernameInput}>
        </input></div>
        <h4>Password</h4>
        <div class="text-input"><input v-model={password} type="password" onInput={onPasswordInput}></input></div>
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
        if(props.errorMsg) {
            return (<div class="submissionErrorMsg">{props.errorMsg}</div>);
        }
    }

    function isUsernameValid(username) {
        if(username.length > 20 || username.length < 3) {
            return false;
        }
        return true;
    }

    function isPasswordValid(password) {
        if(password.length > 256 || password.length < 3) {
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