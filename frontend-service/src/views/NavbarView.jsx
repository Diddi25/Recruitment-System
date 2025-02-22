export default function NavbarView(props) {
    return (<div class="navbar"> 
    <RouterLink to="/login">Login</RouterLink>
    <RouterLink to="/register">Register</RouterLink>
    <RouterLink to="/apply">Apply</RouterLink>
    <RouterLink to="/applications">View applications</RouterLink>
    <RouterLink to="/test-button">Test Advertisement</RouterLink>
    <RouterLink to="/test-login">Test Login</RouterLink>
    <RouterLink to="/test-register">Test Register</RouterLink>
    </div>
    );
}