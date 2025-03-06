import { ref, computed} from 'vue'
import { useRouter } from "vue-router";

export default function NavbarView({store}) {
    const loading = ref(false);
    const message = ref("");
    const router = useRouter();
    const loggedIn = computed(() => store.state.auth.status.loggedIn);

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

    if(loggedIn.value) {
        return (<div class="navbar">
                    <button onClick={logoutACB}>Logout</button>
                    <RouterLink to="/apply">Apply</RouterLink>
                    <RouterLink to="/applications">View applications</RouterLink>
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