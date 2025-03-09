export default function RegisterView(props) {

  let userInfo = {name: null, lastname: null, personNumber: null, 
      email: null, username: null, password: null}

  return (<div class="register">
      <h2>Create an account</h2>        
      <h4>First name</h4>
      <div class="text-input" onInput={onFNInput}><input type="text">
      </input></div>
      <h4>Last Name</h4>
      <div class="text-input" onInput={onLNInput}><input type="text"></input></div>
      <h4>Person number</h4>
      <div class="text-input" onInput={onPNInput}><input type="text"></input></div>
      <h4>Email address</h4>
      <div class="text-input" onInput={onEmailInput}><input type="text">
      </input></div>
      <h4>Username</h4>
      <div class="text-input" onInput={onUsernameInput}><input type="text">
      </input></div>
      <h4>Password</h4>
      <div class="text-input" onInput={onPasswordInput}><input type="text">
      </input></div>
      <button onClick={onSubmit}>Submit</button>
      {getUsernameErrorMsg()}
  </div>);

  function getUsernameErrorMsg(){
      if(props.model.errorMessages.registerSubmission != null) {
          return (<div class="submissionErrorMsg">{props.model.errorMessages.registerSubmission}</div>);
      }
      else return(<div></div>);
  }

  function onFNInput(input){
      userInfo.name = input.target.value;
  }

  function onLNInput(input){
      userInfo.lastname = input.target.value;
  }

  function onPNInput(input){
      userInfo.personNumber = input.target.value;
  }

  function onEmailInput(input){
      userInfo.email = input.target.value;
  }

  function onUsernameInput(input){
      userInfo.username = input.target.value;
  }

  function onPasswordInput(input){
      userInfo.password = input.target.value;
  }

  function onSubmit() {
      props.submitRegistrationInfo(userInfo)
  }
}