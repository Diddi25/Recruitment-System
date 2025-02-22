import { ref, onMounted } from 'vue'
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
    name: "Login",
    components: {
        Form,
        Field,
        ErrorMessage,
    },

    data() {
        const schema = yup.object().shape({
            username: yup.string().required("Username is required!"),
            password: yup.string().required("Password is required!"),
        });

        return {
            loading: false,
            message: "",
            schema,
        };
    },

    computed: {
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
    },

    created() {
        if (this.loggedIn) {
            this.$router.push("/apply");
        }
    },

    methods: {
        handleLogin(user) {
            this.loading = true;
            this.$store.dispatch("auth/login", user).then( //är en action
                () => {
                    this.$router.push("/apply");
                },
                (error) => {
                    this.loading = false;
                    this.message =
                        (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                        error.message ||
                        error.toString();
                }
            );
        },
    },

    template: `
            <div>
                <div>
                    <Form @submit="handleLogin" :validation-schema="schema">
                        <div>
                            <label for="username">Username</label>
                            <Field name="username" type="text" />
                            <ErrorMessage name="username" />
                        </div>
                        <div>
                            <label for="password">Password</label>
                            <Field name="password" type="password" />
                            <ErrorMessage name="password" />
                        </div>
                         <button>Login</button>
                    </Form>
                </div>
            </div>
  ` //end of template, must end with "`"
}