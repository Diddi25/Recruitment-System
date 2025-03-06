import { ref, onMounted, computed } from 'vue'
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import { useRouter } from "vue-router";

export default function RegisterView({store}) {
    let userInfo = {
            name: ref(""),
            lastName: ref(""),
            personNumber: ref(""),
            email: ref(""),
            username: ref(""),
            password: ref(""),
    }
    const router = useRouter();
    const loading = ref(false);
    const message = ref("");
    const successful = ref(false);
    const loggedIn = computed(() => store.state.auth.status.loggedIn);

    const schema = yup.object().shape({
        name: yup
            .string()
            .required("First name is required!")
            .min(2, "First name must be at least 2 characters!")
            .max(50, "First name can be up to 50 characters!"),
        lastName: yup
            .string()
            .required("Last name is required!")
            .min(2, "Last name must be at least 2 characters!")
            .max(50, "Last name can be up to 50 characters!"),
        personNumber: yup
            .string()
            .required("Person number is required!")
            .matches(
                /^[0-9]{10}$/,
                "Person number must be exactly 10 digits!"
            ),
        email: yup
            .string()
            .required("Email is required!")
            .email("Email is invalid!")
            .max(50, "Email can be up to 50 characters!"),
        username: yup
            .string()
            .required("Username is required!")
            .min(3, "Username must be at least 3 characters!")
            .max(20, "Username can be up to 20 characters!"),
        password: yup
            .string()
            .required("Password is required!")
            .min(3, "Password must be at least 3 characters!")
            .max(40, "Password can be up to 40 characters!"),
    });

    const handleRegister = async (user) => {
        user.name = userInfo.name;
        user.lastname = userInfo.lastName;
        user.personNumber = userInfo.personNumber;
        user.email = userInfo.email;
        user.username = userInfo.username;
        user.password = userInfo.password;

        message.value = "";
        successful.value = false;
        loading.value = true;

        try {
            const data = await store.dispatch("auth/register", user);
            message.value = data.message;
            successful.value = true;
            console.log('Registered sucessfully')
            router.push("/login");
        } catch (error) {
            message.value =
                error.response?.data?.message || error.message || error.toString();
            successful.value = false;
        } finally {
            loading.value = false;
        }
    };

    function onFNInput(input){
        userInfo.name = input.target.value;
    }

    function onLNInput(input){
        userInfo.lastName = input.target.value;
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

    return (<div className="register">
             <h2>Create an account</h2>
             <form onSubmit={handleRegister}>
               <div className="text-input">
                 <label htmlFor="name">First name</label><br />
                 <input
                   id="name"
                   type="text"
                   value={userInfo.name.value}
                   onInput={onFNInput}
                 />
               </div>

               <div className="text-input">
                 <label htmlFor="lastName">Last name</label><br />
                 <input
                   id="lastName"
                   type="text"
                   value={userInfo.lastName.value}
                   onInput={onLNInput}
                 />
               </div>

               <div className="text-input">
                 <label htmlFor="personNumber">Person number</label><br />
                 <input
                   id="personNumber"
                   type="text"
                   value={userInfo.personNumber.value}
                   onInput={onPNInput}
                 />
               </div>

               <div className="text-input">
                 <label htmlFor="email">Email address</label><br />
                 <input
                   id="email"
                   type="email"
                   value={userInfo.email.value}
                   onInput={onEmailInput}
                 />
               </div>

               <div className="text-input">
                 <label htmlFor="username">Username</label><br />
                 <input
                   id="username"
                   type="text"
                   value={userInfo.username.value}
                   onInput={onUsernameInput}
                 />
               </div>

               <div className="text-input">
                 <label htmlFor="password">Password</label><br />
                 <input
                   id="password"
                   type="password"
                   value={userInfo.password.value}
                   onInput={onPasswordInput}
                 />
               </div>

               <button type="submit" disabled={loading.value}>
                 {loading.value ? "Loading..." : "Sign Up"}
               </button>
             </form>

             {message.value && (
               <div className={successful.value ? "success" : "error"}>{message.value}</div>
             )}
           </div>);

}