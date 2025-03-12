import { ref, computed} from 'vue'
import { useRouter } from "vue-router";

export default function NavbarView({store}) {
    const loading = ref(false);
    const message = ref("");
    const router = useRouter();
    const loggedIn = computed(() => store.state.auth.status.loggedIn);
    const user = JSON.parse(localStorage.getItem('user'));

    const logoutACB = async (event) => {
        if(loggedIn.value) {
            try {
                await store.dispatch("auth/logout");
                router.push("/login");
            } catch (error) {
                loading.value = false;
                message.value = error.response?.data?.message || error.message || error.toString();
            }
        }
    }

    function roleBasedAccess() {
        if(user.roles == "ROLE_USER") {
            return <RouterLink to="/apply">Apply</RouterLink>;
        } else {
            return <RouterLink to="/applications">View applications</RouterLink>;
        }
    }

    if(loggedIn.value) {
        return (<div class="navbar">
                    <button onClick={logoutACB}>Logout</button>
                    <RouterLink to="/login-success">User</RouterLink>
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
}