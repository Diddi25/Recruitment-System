import { ref, onMounted, computed } from 'vue'
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import { useRouter } from "vue-router";

export default function LoginView({store}) {
    let username = "";
    let password = "";
    const loading = ref(false);
    const message = ref("");
    const router = useRouter();

    const schema = yup.object().shape({
        username: yup.string().required("Username is required!"),
        password: yup.string().required("Password is required!"),
    });

    const loggedIn = computed(() => store.state.auth.status.loggedIn);

    const handleLogin = async (event) => {
        event.preventDefault();
        loading.value = true;
        try {
            await store.dispatch("auth/login", { username: username, password: password });
            router.push("/login-success")
        } catch (error) {
            loading.value = false;
            message.value = error.response?.data?.message || error.message || error.toString();
        }
    };

    function onUsernameInput(input){
        username = input.target.value;
    }

    function onPasswordInput(input){
        password = input.target.value;
    }

    return (
        <div class="login">
            <h2>Log in</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label htmlFor="username">Username</label>
                    <br/>
                    <input type="text"
                           value={username}
                           onInput={onUsernameInput} />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <br />
                    <input type="password"
                           value={password}
                           onInput={onPasswordInput} />
                </div>
                <button type="submit" disabled={loading.value}>Login</button>
            </form>
            {message.value && <p>{message.value}</p>}
        </div>
    );
}