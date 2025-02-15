export default function NavbarView(props) {
    return (<div class="navbar"> 
    <RouterLink to="/login">Login</RouterLink>
    <RouterLink to="/register">Register</RouterLink>
    <RouterLink to="/apply">Apply</RouterLink>
    <RouterLink to="/applications">View applications</RouterLink>
    </div>
    );
}