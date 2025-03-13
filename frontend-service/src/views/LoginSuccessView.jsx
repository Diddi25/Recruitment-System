/**
 * Displays a welcome page to the user
 * @param {*} props 
 * @returns 
 */
export default function LoginSuccessView(props) {

    function greetRoleACB() {
        if(props.role) {
            if(props.role == "ROLE_USER") {
                return "Hi Candidate!"
            } else {
                return "Hi Recruiter!"
            }    
        }
    }

    return (<div>
                <h2>{greetRoleACB()}</h2>
                <h4>Successfully logged in</h4>
            </div>)
}