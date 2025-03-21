
/**
 * Displays a navigation bar
 * @param {*} param0 
 * @returns A navigation bar
 */
export default function NavbarView(props) {
    function roleBasedAccess() {
        if(props.role == "ROLE_USER") {
            return <RouterLink to="/apply">Apply</RouterLink>;
        } else {
            return <RouterLink to="/applications">View applications</RouterLink>;
        }
    }

    if(props.isLoggedIn) {
        return (<div class="navbar">
                    <button onClick={logout}>Logout</button>
                    {roleBasedAccess()}
                </div>)
        ;
    } else {
        return  (<div class="navbar">
                     <RouterLink to="/login">Login</RouterLink>
                     <RouterLink to="/register">Register</RouterLink>
                 </div>)
        ;
    }

    function logout() {
        props.logoutACB();
        window.location.href = "/";
    }
}