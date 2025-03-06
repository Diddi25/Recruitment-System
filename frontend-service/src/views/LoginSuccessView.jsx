
export default function LoginSuccessView(props) {

    const user = JSON.parse(localStorage.getItem('user'));

    function greetRoleACB() {
        if(user.roles == "ROLE_USER") {
            return "Hi Candidate!"
        } else {
            return "Hi Recruiter!"
        }
    }

    return (<div>
                <h2>{greetRoleACB()}</h2>
                <h4>Successfully logged in</h4>
            </div>)
}